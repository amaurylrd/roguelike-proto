package com.engine.physics2d;

//TODO comments cette classe
/**
 * The class {@code Vector} represents a 2 element vector to store floating coordinates like so (x, y).
 *
 * @version 1.0 15 Mar 2021
 * @author Amaury Le Roux Dupeyron
 */
public class Vector {
	/**
	 * The x and y coordinates of this point.
	 */
	private float x, y;

	/**
	 * Construct a new {@code Vector} with the specified values.
	 *
	 * @param x the specified x value
	 * @param y the specified y value
	 */
	public Vector(float x, float y) {
		set(x, y);
	}

	/**
	 * Construct a new {@code Vector} with the default values.
	 * It will initializes to (0,0)
	 */
	public Vector() {
		set(0, 0);
	}

	/**
	 * Construct a new {@code Vector} filled with the specified {@code vector}.
	 *
	 *  @param vector the specified vector
	 */
	public Vector(Vector vector) {
		set(vector.x, vector.y);
	}

	/**
	 * Sets the elements of this {@code Vector}.
	 *
	 * @param x the specified x value
	 * @param y the specified y value
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Fills the elements of this {@code Vector}.
	 * 
	 * @param vector the specified vector
	 * @see set(float x, float y)
	 */
	public void set(Vector vector) {
		set(vector.x, vector.y);
	}

	/**
	 * Gets the x coordinate of this {@code Vector}.
	 * 
	 * @return the value of x
	 * @see setX(float x)
	 */
	public float getX() {
		return x;
	}

	/**
	 * Gets the y coordinate of this {@code Vector}.
	 * 
	 * @return the value of y
	 * @see setY(float y)
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets the value of x to the specified value.
	 * 
	 * @param x the value
	 * @see getX()
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Sets the value of y to the specified value.
	 * 
	 * @param y the value
	 * @see getY()
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * This method translates this {@code Vector} by dx to become (x + dx, y).
	 *
	 * @param dx the x value to be added
	 * @see translate(float dx, float dy)
	 */
	public void translateX(float dx) {
		translate(dx, 0);
	}

	/**
	 * This method translates this {@code Vector} by dy to become (x, y + dy).
	 *
	 * @param dy the y value to be added
	 * @see translate(float dx, float dy)
	 */
	public void translateY(float dy) {
		translate(0, dy);
	}

	/**
	 * This method translate this {@code Vector} by dx and dy to become (x + dx, y + dy).
	 * 
	 * @param dx the x value to be added
	 * @param dy the y value to be added
	 */
	public void translate(float dx, float dy) {
		set(x + dx, y + dy);
	}

	/**
	 * This method translate this {@code Vector} by the vector values.
	 * 
	 * @param vector the specified vector
	 * @see translate(float dx, float dy)
	 */
	public void translate(Vector vector) {
		translate(vector.x, vector.y);
	}

	/**
	 * Duplicates this {@code Vector}.
	 * 
	 * @return a copy of this {@code Vector} 
	 */
	@Override
	public Vector clone() {
		return new Vector(x, y);
	}

	/**
	 * Returns the squared length of a vector.
	 * 
	 * @param vector the specified vector
	 * @return the squared length of the vector
	 */
	public static float lengthSquared(Vector vector) {
		return vector.lengthSquared();
	}

	/**
	 * Returns the squared length of this {@code Vector}.
	 * 
	 * @return the squared length of the vector
	 */
	public float lengthSquared() {
		return (float) Math.sqrt(length());
	}

	/**
	 * Returns the length of a vector.
	 * 
	 * @param vector the specified vector
	 * @return the length of the vector
	 */
	public static float length(Vector vector) {
		return vector.length();
	}

	/**
	 * Returns the length of this {@code Vector}.
	 * 
	 * @return the length of the vector
	 */
	public float length() {
		return x * x + y * y;
	}

	/**
	 * Returns a copy of the vector, which is the normalized vector.
	 * 
	 * @param vector the given vector
	 * @return the normalized vector
	 * @see normalize()
	 */
	public static Vector normalize(Vector vector) {
		Vector normalized = vector.clone();
		normalized.normalize();
		return normalized;
	}
	
	/**
	 * Normalises this {@code Vector}, making it a unit vector.
	 */
	public void normalize() {
		float magnitude = length();
		if (magnitude > 0)
			scale(1 / magnitude);
	}

	/**
	 * Rotates this {@code Vector} by the specified rotation.
	 * 
	 * @param radians the rotation
	 */
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

	/**
	 * Computes the dot product of the specified vectors.
	 * 
	 * @param v1 the first vector
	 * @param v2 the second vector
	 * @return the dot product
	 */
	public static float dot(Vector v1, Vector v2) {
		return v1.dot(v2);
	}

	/**
	 * Computes the dot product of the this {@code Vector} and the specified vector.
	 * 
	 * @param vector the specified vector
	 * @return the dot product
	 */
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
