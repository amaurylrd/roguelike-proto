package engine.geom.shape;

import engine.physics2d.Vector;

public class Circle extends Shape {
	private double radius;
	private Vector origin;

	public double getRadius() {
		return radius;
	}

	@Override
	public void rotate(float theta) {
		rotation -= theta;
	}

	@Override
	public void translateX(double dx) {
		origin.translateX(dx);
	}

	@Override
	public void translateY(double dy) {
		origin.translateY(dy);
	}

	@Override
	public java.awt.Shape stroke() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double area() {
		return Math.PI * radius * radius;
	}

	@Override
	public Vector center() {
		return origin;
	}

	@Override
	public Vector centroid() {
		return origin;
	}

	@Override
	public boolean contains(double x, double y) {
		double dx = origin.getX() - x * origin.getX() - x;
		double dy = origin.getY() - y * origin.getY() - y;
   		return dx + dy <= radius * radius;
	}

	/**
	 * Checks if {@code x} is inside {@code a} and {@code b}.
	 * 
	 * @param x the specicifed value to verify
	 * @param a the first number
	 * @param b the second number
	 * @return <i>true</i> if {@code x} is inside the first and second number, <i>false</i> otherwise
	 */
	protected boolean inside(double x, double a, double b) {
		if (a < b)
			return a <= x && x <= b;
		return b < a ? a >= x && x >= b : a == x;
	}
}