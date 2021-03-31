package com.engine.geom;

import com.engine.physics2d.Vector;

/**
 * The class {@code Shape} provides definitions for objects that represent some form of geometric shape.
 */
public abstract class Shape {
	/**
	 * Translate the {@code Shape} by {@code dx} along the X axis and by {@code dy} along the Y axis.
	 * 
	 * @param dx the x coordinate to be added
	 * @param dy the y coordinate to be added
	 */
	public void translate(float dx, float dy) {
        translateX(dx);
        translateY(dy);
	}
	
	/**
	 * Translates the {@code Polygon} by this {@code vector}.
	 * 
	 * @param vector the vector to be added
	 */
	public void translate(Vector vector) {
		translate(vector.getX(), vector.getY());
	}

	/**
     * Translates this point, at location (x, y) by {@code dx} along the X axis.
	 * Post-translation the point (x, y) becomes (x+dx, y)
     *
     * @param dx the x coordinate to be added
	 * @throws ArithmeticException if {@code dx} is either <i>NaN</i> or infinite
     */
	public abstract void translateX(float dx);

	/**
     * Translates this point, at location (x, y) by {@code dy} along the Y axis.
	 *	Post-translation the point (x, y) becomes (x, y+dy)
	 *
     * @param dy the y coordinate to be added
	 * @throws ArithmeticException if {@code dy} is either <i>NaN</i> or infinite
     */
	public abstract void translateY(float dy);

	/**
	 * Gives the area of this {@code Shape}.
	 * 
	 * @return the area
	 */
	public abstract float area();

	/**
     * Returns the graphical center of this {@code Shape}.
     * 
     * @return the center coordonates
     */
	public abstract Vector center();

	/**
     * Checks whether or not this {@code Shape} contains the point at the specified location (x, y).
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @return <i>true</i> if the point (x, y) is inside this {@code Shape}, <i>false</i> otherwise
     */
	public abstract boolean contains(float x, float y);

	/**
     * Checks whether or not this {@code Shape} contains the point at the specified location (x, y).
     *
     * @param vector the specified location
     * @return <i>true</i> if the point (x, y) is inside this {@code Shape}, <i>false</i> otherwise
     */
	public boolean contains(Vector vector) {
        return contains(vector.getX(), vector.getY());
	}
	
	/**
	 * Checks if {@code x} is inside {@code a} and {@code b}.
	 * 
	 * @param x the specicifed value to verify
	 * @param a the first number
	 * @param b the second number
	 * @return <i>true</i> if {@code x} is inside the first and second number, <i>false</i> otherwise
	 */
	protected boolean inside(float x, float a, float b) {
		if (a < b)
			return a <= x && x <= b;
		return b < a ? a >= x && x >= b : a == x;
	}
}
