package engine.geom.shape;

import engine.geom.Dimension;
import engine.geom.Position;

/**
 * The class {@code Rectangle} specifies an area in space.
 */
public class Rectangle {
    /**
     * The dimension of the rectangle.
     */
    public Dimension dimension;

    /**
     * The top-left corner coordonates of the rectangle.
     */
    public Position position;

    /**
     * Constructs a new {@code Rectangle} to the specified {@code x}, {@code y}, {@code width}, and {@code height}.
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @param width the width of the rectangle
     * @param height the height of the rectangle
     */
    public Rectangle(double x, double y, double width, double height) {
        position = new Position(x, y);
        dimension = new Dimension(width, height);
    }

    /**
     * Sets the bounding Rectangle of this {@code Rectangle} to the specified {@code x}, {@code y}, {@code width}, and {@code height}.
     *
     * @param x the specified x
     * @param y the specified y
     * @param width the specified width
     * @param height the specified height
     * @see setSize(double width, double height)
     * @see setLocation(double x, double y)
     */
    public void setBounds(double x, double y, double width, double height) {
        setSize(width, height);
        setLocation(x, y);
    }

    /**
     * Returns the bounding box of the rectangle.
     *
     * @return the bounds of the rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(position.getX(), position.getY(), dimension.getWidth(), dimension.getHeight());
    }

    /**
     * Sets the size of the rectangle to the specified {@code width} and {@code height}.
     *
     * @param width the specified width
     * @param height the specified height
     * @see getSize()
     */
    public void setSize(double width, double height) {
        dimension.setSize(width, height);
    }

    /**
     * Sets the size of the rectangle to the specified {@code width} and {@code height}.
     *
     * @param width the specified width
     * @param height the specified height
     * @see getSize()
     */
    public void setSize(Dimension size) {
        dimension = size.clone();
    }

    /**
     * Sets the location of the rectangle to the specified coordinates.
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @see getLocation()
     */
    public void setLocation(double x, double y) {
        position.setLocation(x, y);
    }

    /**
     * Sets the location of the rectangle to the specified coordinates.
     *
     * @param location the new top-left corner of the rectangle
     * @see getLocation()
     */
    public void setLocation(Position location) {
        position = location.clone();
    }

    /** 
     * Gets the size of the rectangle.
     *
     * @return the dimension
     * @see setSize
     */
    public Dimension getSize() {
        return dimension.getSize();
    }
    
    /** 
     * Gets the location of the rectangle.
     *
     * @return the coordinates
     * @see setLocation
     */
    public Position getLocation() {
        return position.getLocation();
    }

    /**
     * Returns the center of the rectangle.
     * 
     * @return the center coordonate
     * @see getCenterX
     * @see getCenterY
     */
    public Position getCenter() {
        return new Position(getCenterX(), getCenterY());
    }

    /**
     * Returns the x center coordinate of the rectangle.
     * 
     * @return the x center coordinate
     */
    public double getCenterX() {
        return position.getX() + dimension.getWidth()*0.5;
    }

    /**
     * Returns the y center coordinate of the rectangle.
     * 
     * @return the y center coordinate
     */
    public double getCenterY() {
        return position.getY() + dimension.getHeight()*0.5;
    }
    
    /**
     * Translates the rectangle the indicated distance, to the right along the X coordinate axis, and downward along the Y coordinate axis.
     *
     * @param dx the distance to move along the X axis
     * @param dy the distance to move along the Y axis
     * @see translate(double dx, double dy)
     */
    public void translate(double dx, double dy) {
        position.translate(dx, dy);
    }

    /**
     * Checks whether or not this rectangle contains the point at the specified location (x, y).
     *
     * @param px the specified x coordinate
     * @param py the specified y coordinate
     * @return <i>true</i> if the point (x, y) is inside this rectangle, <i>false</i> otherwise.
     */
    public boolean contains(double px, double py) {
        return px >= position.getX() && py >= position.getY() && px < position.getX() + dimension.getWidth() && py < position.getY() + dimension.getHeight();
    }

    /**
     * Checks whether this rectangle entirely contains the rectangle at the specified location and dimension.
     * 
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @param width the specified width
     * @param height the specified height
     * @return <i>true</i> if the rectangle (x, y, width, height) is inside this rectangle, <i>false</i> otherwise.
     * @see contains(double px, double py)
     */
    public boolean contains(double x, double y, double width, double height) {
        return contains(x, y) && x + width <= dimension.getWidth() && y + height <= dimension.getHeight();
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public Rectangle clone() {
        return getBounds();
    }
    
    /**
     * Indicates whether some other object is "equal to" this one.
     */
    @Override 
    public boolean equals(Object object) {
        if (this == object)
            return true; 
        if (object == null)
            return false; 
        
        if (!(object instanceof Rectangle))
            return false; 
        
        Rectangle downcast = (Rectangle) object;
        return dimension.equals(downcast.dimension) && position.equals(downcast.position);
    }

    /**
     * Returns a string representation of the values of this {@code Rectangle}.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[" + position + ", " + dimension + "]";
    } 
}
