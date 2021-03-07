package engine.physics2d;

public class Vector {
	private float x, y;

	public Vector(float x, float y) {
		set(x, y);
	}

	public Vector() {
		set(0, 0);
	}

	public Vector(Vector vector) {
		set(vector.x, vector.y);
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(Vector vector) {
		set(vector.x, vector.y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void translateX(float dx) {
		translate(dx, 0);
	}

	public void translateY(float dy) {
		translate(0, dy);
	}

	public void translate(float dx, float dy) {
		set(x + dx, y + dy);
	}

	public void translate(Vector vector) {
		translate(vector.x, vector.y);
	}

	@Override
	public Vector clone() {
		return new Vector(x, y);
	}

	public static float magnitude(Vector vector) {
		return vector.magnitude();
	}

	public float magnitude() {
		return (float) Math.sqrt(length());
	}

	public static float length(Vector vector) {
		return vector.length();
	}

	public float length() {
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
		float length = magnitude();
		if (length > 0)
			scale(1 / length);
	}

	public void rotate(float radians) {
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		set(x * cos - y * sin, x * sin + y * cos);
	}

	public static Vector scale(Vector vector, float factor) {
		return new Vector(vector.x * factor, vector.y * factor);
	}

	public void scale(float factor) {
		set(x * factor, y * factor);
	}

	public void scaleX(float factor) {
		set(x * factor, y);
	}

	public void scaleY(float factor) {
		set(x, y * factor);
	}

	public static float dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}

	public float dot(Vector vector) {
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
