package com.dakusuta.graphics.render;

import static com.dakusuta.graphics.render.JRender.gc;

import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Draw {
	private static ColorMode colorMode = ColorMode.RGB;

	private static Color fill;

	private static boolean fillShape = true;

	private static ImageMode imageMode;

	private static PixelWriter pw;

	private static WritableImage wImg;

	public static void biginPath() {
		gc.beginPath();
	}

	public static void colorMode(ColorMode mode) {
		colorMode = mode;
	}

	public static void ellipse(double x, double y, double radius1, double radius2) {
		x -= radius1 / 2;
		y -= radius2 / 2;
		if (fillShape) {
			gc.fillOval(x, y, radius1, radius2);
		} else {
			gc.strokeOval(x, y, radius1, radius2);
		}
	}

	public static void endPath() {
		if (fillShape) {
			gc.fill();
		} else {
			gc.stroke();
		}
		gc.beginPath();
	}

	public static void fill(int gray) {
		fill = Color.grayRgb(gray);
		gc.setFill(fill);
		fillShape = true;
	}

	public static void fill(int gray, double alpha) {
		alpha = alpha <= 0 ? 0 : alpha / 255.0;
		fill = Color.grayRgb(gray, alpha);
		gc.setFill(fill);
		fillShape = true;
	}

	public static void fill(int r, int g, int b) {
		fill = Color.rgb(r, g, b);
		gc.setFill(fill);
		fillShape = true;
	}

	public static void fill(int r, int g, int b, double a) {
		fill = Color.rgb(r, g, b, a / 255);
		gc.setFill(fill);
		fillShape = true;
	}

	public static void image(Image image, double x, double y) {
		if (imageMode.equals(ImageMode.CENTER)) {
			x -= image.getWidth() / 2;
			y -= image.getHeight() / 2;
		}
		gc.drawImage(image, x, y);
	}

	public static void image(Image image, double x, double y, double w, double h) {
		if (imageMode.equals(ImageMode.CENTER)) {
			x -= w / 2;
			y -= h / 2;
		}
		gc.drawImage(image, x, y, w, h);
	}

	public static void image(Image image, double sx, double sy, double sw, double sh, double dx, double dy, double dw, double dh) {
		if (imageMode.equals(ImageMode.CENTER)) {
			dx -= dw / 2;
			dy -= dh / 2;
		}
		gc.drawImage(image, sx, sy, sw, sh, dx, dy, dw, dh);
	}

	public static void imageMode(ImageMode mode) {
		imageMode = mode;
	}

	public static void line(double x1, double y1, double x2, double y2) {
		gc.strokeLine(x1, y1, x2, y2);
	}

	public static void loadPixels() {
		int w = (int) Math.floor(JRender.width);
		int h = (int) Math.floor(JRender.height);
		wImg = new WritableImage(w, h);
		JRender.pixels = new int[w * h * 4];
		pw = wImg.getPixelWriter();
	}

	public static void noFill() {
		fillShape = false;
	}

	public static void point(double x, double y) {
		double width = gc.getLineWidth();

		gc.strokeOval(x - 0.5 - width / 2, y - width / 2, width, width);
	}

	public static void rect(double x, double y, double w, double h) {
		if (fillShape)
			gc.fillRect(x, y, w, h);
		else
			gc.strokeRect(x, y, w, h);
	}

	public static void restore() {
		gc.restore();
	}

	public static void rotate(double angle) {
		gc.rotate(Math.toDegrees(angle));
	}

	public static void save() {
		gc.save();
	}

	public static void stroke(int gray) {
		gc.setStroke(Color.grayRgb(gray));
	}

	public static void stroke(int gray, double alpha) {
		alpha = alpha <= 0 ? 0 : alpha / 255.0;
		if (alpha < 0) alpha = 0;
		gc.setStroke(Color.grayRgb(gray, alpha));
	}

	public static void stroke(int red, int green, int blue) {
		if (colorMode.equals(ColorMode.RGB))
			gc.setStroke(Color.rgb(red, green, blue));
		else {
			double r = red;
			double g = green <= 0 ? 0 : green / 255.0;
			double b = blue <= 0 ? 0 : blue / 255.0;

			gc.setStroke(Color.hsb(r, g, b));
		}
	}

	public static void stroke(int red, int green, int blue, double alpha) {
		alpha = alpha <= 0 ? 0 : alpha / 255.0;
		if (colorMode.equals(ColorMode.RGB))
			gc.setStroke(Color.rgb(red, green, blue, alpha));
		else {
			double r = red;
			double g = green <= 0 ? 0 : green / 255.0;
			double b = blue <= 0 ? 0 : blue / 255.0;
			gc.setStroke(Color.hsb(r, g, b, alpha));
		}
	}

	public static void strokeWeight(double weight) {
		gc.setLineWidth(weight);
	}

	public static void translate(double x, double y) {
		gc.translate(x, y);
	}

	private static void updateImage() {
		int w = (int) Math.floor(JRender.width);
		int h = (int) Math.floor(JRender.height);
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int index = (x + y * w) * 4;
				int r = JRender.pixels[index];
				if (r > 255) r = 255;
				int g = JRender.pixels[index + 1];
				if (g > 255) g = 255;
				int b = JRender.pixels[index + 2];
				if (b > 255) b = 255;
				double a = JRender.pixels[index + 3] / 255.0;
				pw.setColor(x, y, Color.rgb(r, g, b, a));
			}
		}
	}

	public static void updatePixels() {
		updateImage();
		gc.drawImage(wImg, 0, 0);
	}

	public static void vertex(double x, double y) {
		gc.lineTo(x, y);
		gc.moveTo(x, y);
	}
}
