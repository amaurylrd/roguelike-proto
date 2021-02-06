package engine.physics2d;

public class Vector {
	private double x, y;

	public Vector(double x, double y) {
		set(x, y);
	}

	public Vector() {
		set(0, 0);
	}

	public Vector(Vector vector) {
		set(vector.x, vector.y);
	}

	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void set(Vector vector) {
		set(vector.x, vector.y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void translateX(double dx) {
		translate(dx, 0);
	}

	public void translateY(double dy) {
		translate(0, dy);
	}

	public void translate(double dx, double dy) {
		set(x + dx, y + dy);
	}

	public void translate(Vector vector) {
		translate(vector.x, vector.y);
	}

	@Override
	public Vector clone() {
		return new Vector(x, y);
	}

	public static double magnitude(Vector vector) {
		return vector.magnitude();
	}

	public double magnitude() {
		return Math.sqrt(length());
	}

	public static double length(Vector vector) {
		return vector.length();
	}

	public double length() {
		return x * x + y * y;
	}

	public static Vector normalize(Vector vector) {
		Vector normalized = vector.clone();
		normalized.normalize();
		return normalized;
	}
	
	/**
	 * Normalises this {@code Vector}, making it a unit vector.
	 */
	public void normalize() {
		double length = magnitude();
		if (length > 0)
			scale(1 / length);
	}

	public void rotate(double radians) {
		double cos = Math.cos(radians);
		double sin = Math.sin(radians);
		set(x * cos - y * sin, x * sin + y * cos);
	}

	public static Vector scale(Vector vector, double factor) {
		return new Vector(vector.x * factor, vector.y * factor);
	}

	public void scale(double factor) {
		set(x * factor, y * factor);
	}

	public void scaleX(double factor) {
		set(x * factor, y);
	}

	public void scaleY(double factor) {
		set(x, y * factor);
	}

	public static double dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}

	public double dot(Vector vector) {
		return x * vector.x + y * vector.y;
	}

	public static Vector sub(Vector a, Vector b) {
		return new Vector(a.x - b.x, a.y - b.y);
	}

	public void sub(Vector vector) {
		set(x - vector.x, y - vector.y);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + x + ", " + y + "]";
	}
}
