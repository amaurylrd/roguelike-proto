package engine.geom.shape;

import engine.physics2d.Vector;

/**
 * The class {@code Shape} provides definitions for objects that represent some form of geometric shape.
 */
public abstract class Shape {
	/**
     * The rotation in radians applied to this {@code Shape}. 
     */
	protected float rotation = 0f;

	/**
     * Rotates the Z axis of this {@code Shape} in counter-clockwise direction.
     * The point (x, y) becomes (xcosθ−ysinθ, xsinθ+ycosθ).
     * 
     * @param theta the angle of rotation in radians
     */
	public abstract void rotate(float theta);

	/**
     * Gets the rotation of this {@code Shape} in radians.
     * 
     * @return the rotation theta
     */
    public float getRotation() {
        return rotation;
	}
	
	/**
	 * Translate the {@code Shape} by {@code dx} along the X axis and by {@code dy} along the Y axis.
	 * 
	 * @param dx the x coordinate to be added
	 * @param dy the y coordinate to be added
	 */
	public void translate(double dx, double dy) {
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
	public abstract void translateX(double dx);

	/**
     * Translates this point, at location (x, y) by {@code dy} along the Y axis.
	 *	Post-translation the point (x, y) becomes (x, y+dy)
	 *
     * @param dy the y coordinate to be added
	 * @throws ArithmeticException if {@code dy} is either <i>NaN</i> or infinite
     */
	public abstract void translateY(double dy);

	/**
	 * Returns the path of this {@code Shape} for draw calls.
	 * 
	 * @return the path of this {@code Shape}
	 */
	public abstract java.awt.Shape stroke();

	/**
	 * Gives the area of this {@code Shape}.
	 * 
	 * @return the area
	 */
	public abstract double area();

	/**
     * Returns the graphical center of this {@code Shape}.
     * 
     * @return the center coordonates
     */
	public abstract Vector center();

	/**
     * Returns the mass center of this {@code Polygon}.
     * 
     * @return the center coordonates
     */
	public abstract Vector centroid();

	/**
     * Checks whether or not this {@code Shape} contains the point at the specified location (x, y).
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @return <i>true</i> if the point (x, y) is inside this {@code Shape}, <i>false</i> otherwise
     */
	public abstract boolean contains(double x, double y);

	/**
     * Checks whether or not this {@code Shape} contains the point at the specified location (x, y).
     *
     * @param vector the specified location
     * @return <i>true</i> if the point (x, y) is inside this {@code Shape}, <i>false</i> otherwise
     */
	public boolean contains(Vector vector) {
        return contains(vector.getX(), vector.getY());
    }
}
