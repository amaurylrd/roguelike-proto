package engine.geom.shape;

import engine.physics2d.Vector;
import java.awt.geom.Ellipse2D;

public class Circle extends Shape {
	private float radius;
	private Vector origin;

	public float getRadius() {
		return radius;
	}

	@Override
	public void rotate(float theta) {
		rotation -= theta;
	}

	@Override
	public void translateX(float dx) {
		origin.translateX(dx);
	}

	@Override
	public void translateY(float dy) {
		origin.translateY(dy);
	}

	@Override
	public java.awt.Shape stroke() {
		return new Ellipse2D.Float(origin.getX(), origin.getY(), radius, radius);
	}

	@Override
	public float area() {
		return (float) (Math.PI * radius * radius);
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
	public boolean contains(float x, float y) {
		float dx = origin.getX() - x * origin.getX() - x;
		float dy = origin.getY() - y * origin.getY() - y;
   		return dx + dy <= radius * radius;
	}
}