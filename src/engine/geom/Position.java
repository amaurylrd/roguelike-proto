package engine.geom;

/**
 * The {@code Position} class encapsulates the 2D coordinates (x, y) of a component.
 */
public class Position {
    /**
     * The x coordinate of the point.
     */
    public int x;
    
    /**
     * The y coordinate of the point.
     */
    public int y;

    /**
     * Constructs and initializes a {@code Position} with the specified coordinates.
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @see setLocation(int x, int y)
     */
    public Position(int x, int y) {
        setLocation(x, y);
    }

    /**
     * Returns the x coordinate of the point.
     * 
     * @return the x coordinate
     * @see setX(int x)
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y coordinate of the point.
     * 
     * @return the y coordinate
     * @see setY(int y)
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the x coordinate of the point to the specified coordinate.
     *
     * @param x the new x coordinate
     * @see getX()
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y coordinate of the point to the specified coordinate.
     *
     * @param y the new x coordinate
     * @see getY()
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the location of the point to the specified coordinates.
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @see setX(int x)
     * @see setY(int y)
     */
    public void setLocation(int x, int y) {
        setX(x);
        setY(y);
    }

    /** 
     * Gets the location of the point.
     *
     * @return a new instance of {@code Position} with the same coordinates.
     * @see setLocation(int x, int y)
     */
    public Position getLocation() {
        return new Position(x, y);
    }

    /**
     * Translates this point, at location (x,y) by {@code dx} along the x axis and by {@code dy} along the y axis
     *
     * @param dx the x coordinate to add
     * @param dy the y coordinate to add
     * @see translateX(int dx)
     * @see translateY(int dy)
     */
    public void translate(int dx, int dy) {
        translateX(dx);
        translateY(dy);
    }

    /**
     * Translates this point, at location (x,y) by {@code dx} along the x axis
     * 
     * @param dx the x coordinate to add
     * @see setX(int x)
     */
    public void translateX(int dx) {
        setX(this.x + dx);
    }

    /**
     * Translates this point, at location (x,y) by {@code dy} along the y axis
     * 
     * @param dy the y coordinate to add
     * @see setY(int y)
     */
    public void translateY(int dy) {
        setY(this.y + dy);
    }

    /**
     * Returns a string with the values of {@code x} and {@code y} fields.
     * 
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[x:" + x + ", y:" + y + "]";
    }
}
