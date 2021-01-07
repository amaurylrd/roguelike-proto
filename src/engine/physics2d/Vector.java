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

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		set(x, y);
	}

	public void setY(double y) {
		set(x, y);
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
		return vector.normalize();
	}
	
	public Vector normalize() {
		Vector normalized = this.clone();
		double length = normalized.magnitude();
        return length > 0 ? normalized.scale(1/length) : normalized;
	}

	public static Vector scale(Vector vector, double factor) {
		return vector.scale(factor);
	}

	public Vector scale(double factor) {
		return new Vector(x * factor, y * factor);
	}

	public static double dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}

	public double dot(Vector vector) {
		return x * vector.x + y * vector.y;
	}

	public static Vector sub(Vector v1, Vector v2) {
		return v1.sub(v2);
	}

	public Vector sub(Vector vector) {
		return new Vector(x - vector.x, y - vector.y);
	}

	public static Vector negate(Vector vector) {
		return vector.negate();
	}

	public Vector negate() {
		return scale(-1);
	}

	@Override
	public String toString() {
		return this.getClass().getName() + "[" + x + ", " + y + "]";
	}
}
