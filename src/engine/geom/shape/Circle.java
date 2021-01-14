package engine.geom.shape;

import engine.physics2d.Vector;
import java.awt.geom.Ellipse2D;

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
		return new Ellipse2D.Double(origin.getX(), origin.getY(), radius, radius);
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
}