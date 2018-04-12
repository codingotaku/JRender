package com.dakusuta.graphics.render.physics;

import com.sun.istack.internal.NotNull;

public class JVector {
	public static JVector add(@NotNull JVector vector1, @NotNull JVector vector2, JVector target) {
		if (target == null) target = new JVector();
		target.x = vector1.x + vector2.x;
		target.y = vector1.y + vector2.y;
		target.z = vector1.z + vector2.z;
		return target;
	}

	public static JVector copy(@NotNull JVector source, JVector target) {
		if (target == null) target = new JVector();
		target = source.copy();
		return target;
	}

	public static double dot(@NotNull JVector v1, @NotNull JVector v2) {
		return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
	}

	public double dot(@NotNull JVector v) {
		return this.x * v.x + this.y * v.y + this.z * v.z;
	}

	public static JVector cross(@NotNull JVector v1, @NotNull JVector v2) {
		return new JVector(v1.x * v2.y, v1.y * v2.z, v1.z * v2.x);
	}

	public JVector cross(@NotNull JVector v) {
		return new JVector(this.x * v.y, this.y * v.z, this.z * v.x);
	}

	public static JVector div(@NotNull JVector v, double n) {
		JVector target = new JVector();
		target.x = v.x / n;
		target.y = v.y / n;
		target.z = v.z / n;
		return target;
	}

	public static JVector mult(@NotNull JVector v, double n) {
		JVector target = new JVector();
		target.x = v.x * n;
		target.y = v.y * n;
		target.z = v.z * n;
		return target;
	}

	public static JVector sub(@NotNull JVector vector1, @NotNull JVector vector2, JVector target) {
		if (target == null) target = new JVector();
		target.x = (vector1.x - vector2.x);
		target.y = (vector1.y - vector2.y);
		target.z = (vector1.z - vector2.z);
		return target;
	}

	public double x;

	public double y;

	public double z;

	public JVector() {
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public JVector(double d, double e) {
		this.x = d;
		this.y = e;
		this.z = 0;
	}

	public JVector(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public JVector(@NotNull JVector vector) {
		this.x = vector.x;
		this.y = vector.y;
		this.z = vector.z;
	}

	public JVector add(double x, double y) {
		this.x += x;
		this.y += y;
		return this;
	}

	public JVector add(double x, double y, double z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	public JVector add(@NotNull JVector vector) {
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		return this;
	}

	public static JVector add(@NotNull JVector vector1, @NotNull JVector vector2) {
		JVector target = new JVector();
		target.x = vector1.x + vector2.x;
		target.y = vector1.y + vector2.y;
		target.z = vector1.z + vector2.z;
		return target;
	}

	public JVector copy() {
		JVector target = new JVector();
		target.x = this.x;
		target.y = this.y;
		target.z = this.z;
		return target;
	}

	public JVector div(double n) {
		this.x /= n;
		this.y /= n;
		this.z /= n;
		return this;
	}

	public double magnitude() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double magnitudeSq() {
		return x * x + y * y + z * z;
	}

	public JVector mult(double n) {
		this.x *= n;
		this.y *= n;
		this.z *= n;
		return this;
	}

	public JVector sub(double x, double y) {
		this.x -= x;
		this.y -= y;
		return this;
	}

	public JVector sub(double x, double y, double z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public JVector sub(@NotNull JVector vector) {
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		return this;
	}

	public static JVector sub(@NotNull JVector vector1, @NotNull JVector vector2) {
		JVector target = new JVector();
		target.x = (vector1.x - vector2.x);
		target.y = (vector1.y - vector2.y);
		target.z = (vector1.z - vector2.z);
		return target;
	}

	public double heading() {
		return Math.atan2(y, x);
	}

	public JVector set(double x, double y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public JVector set(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public JVector setMag(double newMag) {
		double mag = magnitude();
		this.x *= newMag / mag;
		this.y *= newMag / mag;
		return this;
	}

	public static JVector Random2D() {
		return fromAngle(Math.random() * Math.PI * 2);
	}

	public static JVector fromAngle(double angle) {
		return fromAngle(angle, 1);
	}

	public static JVector fromAngle(double angle, double length) {
		return new JVector(length * Math.cos(angle), length * Math.sin(angle), 0);
	}

	public JVector limit(double max) {
		double mSq = magnitudeSq();
		if (mSq > max * max) {
			this.div(Math.sqrt(mSq)) // normalize it
					.mult(max);
		}
		return this;
	}

	public JVector normalize() {
		double len = magnitude();
		if (len != 0) this.mult(1 / len);
		return this;
	}
}
