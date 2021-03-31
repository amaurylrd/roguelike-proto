package com.engine.geom;

import com.engine.physics2d.Vector;

/**
 * The class {@code Rectangle} specifies an area in space.
 */
public class Rectangle extends Shape {
    private Vector point;

    /**
     * The {@code Dimension} of the rectangle.
     */
    private Dimension size;

    /**
     * Constructs a new {@code Rectangle} to the specified {@code x}, {@code y},
     * {@code width}, and {@code height}.
     *
     * @param x the specified x coordinate
     * @param y he specified y coordinate
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(float x, float y, float width, float height) {
        point = new Vector(x, y);
        size = new Dimension(width, height);
    }

    /**
     * Returns the x coordinate of the coordinates.
     * 
     * @return the x coordinate
     * @see setX(float x)
     */
    public float getX() {
        return point.getX();
    }

    /**
     * Returns the y coordinate of the coordinates.
     * 
     * @return the y coordinate
     * @see setY(float y)
     */
    public float getY() {
        return point.getY();
    }

    /**
     * Returns the width of the dimension.
     * 
     * @return the width of the dimension
     * @see setWidth(float width)
     */
    public float getWidth() {
        return size.getWidth();
    }

    /**
     * Returns the height of the dimension.
     * 
     * @return the height of the dimension
     * @see setHeight(float height)
     */
    public float getHeight() {
        return size.getHeight();
    }

    /**
     * Gets the location of the rectangle.
     *
     * @return the coordinates
     * @see setLocation(float x, float y)
     */
    public Vector getLocation() {
        return point.clone();
    }

    /**
     * Gets the size of the rectangle.
     *
     * @return the dimension
     * @see setSize(float width, float height)
     */
    public Dimension getSize() {
        return size.getSize();
    }

    /**
     * Sets the x coordinate of the point to the specified coordinate.
     *
     * @param x the new x coordinate
     * @see getX()
     */
    public void setX(float x) {
        float offsetX = Math.abs(x - getX());
        translateX(offsetX);
    }

    /**
     * Sets the y coordinate of the point to the specified coordinate.
     *
     * @param y the new x coordinate
     * @see getY()
     */
    public void setY(float y) {
        float offsetY = Math.abs(y - getY());
        translateY(offsetY);
    }

    /**
     * Sets the width of the dimension to the specified {@code width}.
     * 
     * @param width the specified width
     * @throws ArithmeticException if the width is NaN or infinite
     * @throws IllegalArgumentException if the width is negative
     * @see getWidth()
     */
    public void setWidth(float width) {
        size.setWidth(width);
    }

    /**
     * Sets the height of the dimension to the specified {@code height}.
     * 
     * @param height the specified height
     * @throws ArithmeticException if the height is NaN or infinite
     * @throws IllegalArgumentException if the height is negative
     * @see getHeight()
     */
    public void setHeight(float height) {
        size.setHeight(height);
    }

    /**
     * Sets the bounding Rectangle of this {@code Rectangle} to the specified
     * {@code x}, {@code y}, {@code width}, and {@code height}.
     *
     * @param x the specified x
     * @param y the specified y
     * @param width the specified width
     * @param height the specified height
     * @see setSize(float width, float height)
     * @see setLocation(float x, float y)
     */
    public void setBounds(float x, float y, float width, float height) {
        setLocation(x, y);
        setSize(width, height);
    }

    /**
     * Sets the location of the rectangle to the specified coordinates.
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @see getLocation()
     */
    public void setLocation(float x, float y) {
        point.set(x, y);
    }

    /**
     * Sets the location of the rectangle to the specified coordinates.
     *
     * @param vector the specified coordinates
     * @see getLocation()
     */
    public void setLocation(Vector vector) {
        point.set(vector);
    }

    /**
     * Sets the size of the rectangle to the specified {@code width} and
     * {@code height}.
     *
     * @param width  the specified width
     * @param height the specified height
     * @see getSize()
     */
    public void setSize(float width, float height) {
        size.setSize(width, height);
    }

    /**
     * Sets the size of the rectangle to the specified {@code dimension}.
     *
     * @param dimension the specified dimension
     * @see getSize()
     */
    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    /**
     * Translates this point, at location (x, y) by {@code dx} along the X axis.
	 * The point (x, y) becomes (x + dx, y).
     *
     * @param dx the x coordinate to be added
     */
	@Override
	public void translateX(float dx) {
		point.translateX(dx);
	}

    /**
     * Translates this point, at location (x, y) by {@code dy} along the Y axis.
	 * The point (x, y) becomes (x, y + dy).
	 *
     * @param dy the y coordinate to be added
     */
    @Override
    public void translateY(float dy) {
        point.translateY(dy);
    }

    /**
	 * Gives the area of this {@code Rectangle}.
	 * 
	 * @return the area
	 */
    @Override
    public float area() {
        return getWidth() * getHeight();
    }

    /**
     * Returns the graphical center of this {@code Rectangle}.
     * 
     * @return the center (x, y)
     */
    @Override
    public Vector center() {
        return new Vector(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    /**
     * Checks whether or not this {@code Rectangle} contains the point at the specified location (x, y).
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @return <i>true</i> if the point (x, y) is inside this {@code Rectangle}, <i>false</i> otherwise
     */
    @Override
    public boolean contains(float x, float y) {
        return y >= getY() && y <= getY() + getHeight() && x >= getX() && x <= getX() + getWidth();
    }
    
    /**
	 * Checks whenever two rectangles intersect. It considers overlapping not an insertection.
	 * 
	 * @param rectangle the specified rectangle
	 * @return <i>true</i> if the rectangle intersects this {@code Rectangle}, <i>false</i> otherwise
	 */
    public boolean intersects(Rectangle rectangle) {
        float dx = Math.abs(center().getX() - rectangle.center().getX());
        float dy = Math.abs(center().getY() - rectangle.center().getY());
        return dx + dx < getWidth() + rectangle.getWidth() && dy + dy < getHeight() + rectangle.getHeight();
    }

    /**
     * Returns the bounding box of the rectangle.
     *
     * @return the bounds of the rectangle
     */
    @Override
    public Rectangle clone() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
	 * Returns the representation of this {@code Shape} as {@code String}.
	 * 
	 * @return the {@code String} representing this object
	 */
	@Override
	public String toString() {
		String str = this.getClass().getName();
        str += "[x:" + getX() + ", y:" + getY() + ", width: " + getWidth() + ", height: " + getHeight() + "]";
        return str;
	}
}
