package com.dakusuta.graphics.render.physics;

import java.util.Random;

public class Calc {
	private static final int PERLIN_SIZE = 4095;
	private static Random rn = new Random();
	private static boolean previous;
	private static double y2;
	private static double[] perlin;
	private static double perlin_octaves = 4; // default to medium smooth
	private static double perlin_amp_falloff = 0.5; // 50% reduction/octave
	private static final int PERLIN_YWRAPB = 4;
	private static final int PERLIN_YWRAP = 1 << PERLIN_YWRAPB;
	private static final int PERLIN_ZWRAPB = 8;
	private static final int PERLIN_ZWRAP = 1 << PERLIN_ZWRAPB;

	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

	public static double distance(double x1, double y1, double z1, double x2, double y2, double z2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2) + Math.pow((z2 - z1), 2));
	}

	public static double distance(JVector vector1, JVector vector2) {
		return distance(vector1.x, vector1.y, vector1.z, vector2.x, vector2.y, vector2.z);
	}

	public static int random(int max) {
		return rn.nextInt(max);
	}

	public static double random(double min, double max) {
		return rn.nextDouble() * (max - min) + min;
	}

	public static double random(double max) {
		return rn.nextDouble() * max;
	}

	public static int random(int min, int max) {
		return rn.nextInt(max - min) + min;
	}

	public static double map(double n, double start1, double stop1, double start2, double stop2) {
		return ((n - start1) / (stop1 - start1)) * (stop2 - start2) + start2;
	}

	public static double randomGaussian(double mean, double sd) {
		double y1, x1, x2, w;
		if (previous) {
			y1 = y2;
			previous = false;
		} else {
			do {
				x1 = random(2.0) - 1;
				x2 = random(2.0) - 1;
				w = x1 * x1 + x2 * x2;
			} while (w >= 1);
			w = Math.sqrt(-2 * Math.log(w) / w);
			y1 = x1 * w;
			y2 = x2 * w;
			previous = true;
		}
		return y1 * sd + mean;
	}

	public static double randomGaussian(double mean) {
		double y1, x1, x2, w;
		if (previous) {
			y1 = y2;
			previous = false;
		} else {
			do {
				x1 = random(2.0) - 1;
				x2 = random(2.0) - 1;
				w = x1 * x1 + x2 * x2;
			} while (w >= 1);
			w = Math.sqrt(-2 * Math.log(w) / w);
			y1 = x1 * w;
			y2 = x2 * w;
			previous = true;
		}
		return y1 + mean;
	}

	public static double randomGaussian() {
		double y1, x1, x2, w;
		if (previous) {
			y1 = y2;
			previous = false;
		} else {
			do {
				x1 = random(2.0) - 1;
				x2 = random(2.0) - 1;
				w = x1 * x1 + x2 * x2;
			} while (w >= 1);
			w = Math.sqrt(-2 * Math.log(w) / w);
			y1 = x1 * w;
			y2 = x2 * w;
			previous = true;
		}
		return y1;
	}

	public static double constrain(double n, double low, double high) {
		return Math.max(Math.min(n, high), low);
	}

	public static double noise(double x) {
		return noise(x, 0, 0);
	}

	public static double noise(double x, double y) {
		return noise(x, y, 0);
	}

	public static double noise(double x, double y, double z) {
		if (perlin == null) {
			perlin = new double[PERLIN_SIZE + 1];
			for (int i = 0; i < PERLIN_SIZE + 1; i++) {
				perlin[i] = Math.random();
			}
		}

		if (x < 0) {
			x = -x;
		}
		if (y < 0) {
			y = -y;
		}
		if (z < 0) {
			z = -z;
		}

		int xi = (int) Math.floor(x), yi = (int) Math.floor(y), zi = (int) Math.floor(z);
		double xf = x - xi;
		double yf = y - yi;
		double zf = z - zi;
		double rxf, ryf;

		double r = 0;
		double ampl = 0.5;

		double n1, n2, n3;

		for (int o = 0; o < perlin_octaves; o++) {
			int of = xi + (yi << PERLIN_YWRAPB) + (zi << PERLIN_ZWRAPB);

			rxf = scaled_cosine(xf);
			ryf = scaled_cosine(yf);

			n1 = perlin[of & PERLIN_SIZE];
			n1 += rxf * (perlin[(of + 1) & PERLIN_SIZE] - n1);
			n2 = perlin[(of + PERLIN_YWRAP) & PERLIN_SIZE];
			n2 += rxf * (perlin[(of + PERLIN_YWRAP + 1) & PERLIN_SIZE] - n2);
			n1 += ryf * (n2 - n1);

			of += PERLIN_ZWRAP;
			n2 = perlin[of & PERLIN_SIZE];
			n2 += rxf * (perlin[(of + 1) & PERLIN_SIZE] - n2);
			n3 = perlin[(of + PERLIN_YWRAP) & PERLIN_SIZE];
			n3 += rxf * (perlin[(of + PERLIN_YWRAP + 1) & PERLIN_SIZE] - n3);
			n2 += ryf * (n3 - n2);

			n1 += scaled_cosine(zf) * (n2 - n1);

			r += n1 * ampl;
			ampl *= perlin_amp_falloff;
			xi <<= 1;
			xf *= 2;
			yi <<= 1;
			yf *= 2;
			zi <<= 1;
			zf *= 2;

			if (xf >= 1.0) {
				xi++;
				xf--;
			}
			if (yf >= 1.0) {
				yi++;
				yf--;
			}
			if (zf >= 1.0) {
				zi++;
				zf--;
			}
		}
		return r;
	}

	
	public static void noiseDetail(double lod) {
		if (lod > 0) {
			perlin_octaves = lod;
		}
	}
	
	public static void noiseDetail(double lod, double falloff) {
		if (lod > 0) {
			perlin_octaves = lod;
		}
		if (falloff > 0) {
			perlin_amp_falloff = falloff;
		}
	}

	private static double scaled_cosine(double i) {
		return 0.5 * (1.0 - Math.cos(i * Math.PI));
	}
}