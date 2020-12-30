package engine.geom.shape;

import java.awt.geom.Point2D;

import engine.physics2d.Vector;

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
    abstract void rotate(float theta);

    /**
     * Gets the rotation of this {@code Shape} in radians.
     * 
     * @return the rotation theta
     */
    public float getRotation() {
        return rotation;
    }

    /**
	 * Gives the area of this {@code Shape}.
	 * 
	 * @return the area
	 */
    abstract float area();

    /**
     * Returns the graphical center of this {@code Shape}.
     * 
     * @return the center coordonates
     */
    abstract Point2D.Float center();
    
    /**
     * Checks whether or not this {@code Shape} contains the point at the specified location (x, y).
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @return <i>true</i> if the point (x, y) is inside this {@code Shape}, <i>false</i> otherwise
     */
    abstract boolean contains(float x, float y);

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
    abstract void translateX(float dx);

    /**
     * Translates this point, at location (x, y) by {@code dy} along the Y axis.
	 *	Post-translation the point (x, y) becomes (x, y+dy)
	 *
     * @param dy the y coordinate to be added
	 * @throws ArithmeticException if {@code dy} is either <i>NaN</i> or infinite
     */
    abstract void translateY(float dy);

    /**
	 * Returns the path of this {@code Shape} for draw calls.
	 * 
	 * @return the path of this {@code Shape}
	 */
    abstract java.awt.Shape stroke();
}
