package engine.geom;

/**
 * The {@code Position} class encapsulates the 2D coordinates (x, y) of a component.
 */
public class Position {
    /**
     * The coordinates (x, y) of the point.
     */
    private double[] position;

    /**
     * Constructs and initializes a {@code Position} with the specified coordinates.
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @see setLocation(double x, double y)
     */
    public Position(double x, double y) {
        position = new double[] {x, y};
    }

    /**
     * Returns the x coordinate of the coordinates.
     * 
     * @return the x coordinate
     * @see setX(double x)
     */
    public double getX() {
        return position[0];
    }

    /**
     * Sets the x coordinate of the point to the specified coordinate.
     *
     * @param x the new x coordinate
     * @see getX()
     */
    public void setX(double x) {
        position[0] = x;
    }

    /**
     * Returns the y coordinate of the coordinates.
     * 
     * @return the y coordinate
     * @see setY(double y)
     */
    public double getY() {
        return position[1];
    }

    /**
     * Sets the y coordinate of the point to the specified coordinate.
     *
     * @param y the new x coordinate
     * @see getY()
     */
    public void setY(double y) {
        position[1] = y;
    }

    /**
     * Sets the location of the point to the specified coordinates.
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @see setX(double x)
     * @see setY(double y)
     */
    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    /** 
     * Gets the location of the point.
     *
     * @return a new instance of {@code Position} with the same coordinates.
     * @see setLocation(double x, double y)
     */
    public Position getLocation() {
        return new Position(getX(), getY());
    }

    /**
     * Translates this point, at location (x,y) by {@code dx} along the x axis and by {@code dy} along the y axis
     *
     * @param dx the x coordinate to add
     * @param dy the y coordinate to add
     * @see translateX(double dx)
     * @see translateY(double dy)
     */
    public void translate(double dx, double dy) {
        translateX(dx);
        translateY(dy);
    }

    /**
     * Translates this point, at location (x,y) by {@code dx} along the x axis
     * 
     * @param dx the x coordinate to add
     * @see setX(double x)
     */
    public void translateX(double dx) {
        setX(getX() + dx);
    }

    /**
     * Translates this point, at location (x,y) by {@code dy} along the y axis
     * 
     * @param dy the y coordinate to add
     * @see setY(double y)
     */
    public void translateY(double dy) {
        setY(getY() + dy);
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public Position clone() {
        return getLocation();
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
        if (!(object instanceof Position))
            return false;
        Position downcast = (Position) object;
        return getX() == downcast.getX() && getY() == downcast.getY();
    }

    /**
     * Returns a string with the values of {@code x} and {@code y} fields.
     * 
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[x:" + getX() + ", y:" + getY() + "]";
    }
}