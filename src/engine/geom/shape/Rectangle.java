package engine.geom.shape;

import java.awt.geom.Point2D;

import engine.geom.Dimension;


/**
 * The class {@code Rectangle} specifies an area in space.
 */
public class Rectangle extends Polygon {
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
    public Rectangle(double x, double y, double width, double height) {
        super(new double[] {x, x + width, x + width, x}, new double[] {y, y, y + height, y + height});
        size = new Dimension(width, height);
    }
    
    /**
     * Returns the x coordinate of the coordinates.
     * 
     * @return the x coordinate
     * @see setX
     */
    public double getX() {
        return points[0].x;
    }

    /**
     * Returns the y coordinate of the coordinates.
     * 
     * @return the y coordinate
     * @see setY
     */
    public double getY() {
        return points[0].y;
    }

    /**
     * Returns the width of the dimension.
     * 
     * @return the width of the dimension
     * @see setWidth
     */
    public double getWidth() {
        return size.getWidth();
    }

    /**
     * Returns the height of the dimension.
     * 
     * @return the height of the dimension
     * @see setHeight
     */
    public double getHeight() {
        return size.getHeight();
    }

    /**
     * Returns the bounding box of the rectangle.
     *
     * @return the bounds of the rectangle
     */
    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Gets the location of the rectangle.
     *
     * @return the coordinates
     * @see setLocation
     */
    public Point2D.Double getLocation() {
        return new Point2D.Double(getX(), getY());
    }

    /**
     * Gets the size of the rectangle.
     *
     * @return the dimension
     * @see setSize
     */
    public Dimension getSize() {
        return size.getSize();
    }

    /**
     * Gets the rotation of any rectangle in radians.
     * 
     * @return the rotation theta
     */
    public static double getRotation(double x, double y, double oppositeX, double oppositeY) {
        return Math.atan((x - oppositeX) / (y - oppositeY));
    }

    /**
     * Sets the x coordinate of the point to the specified coordinate.
     *
     * @param x the new x coordinate
     * @see getX
     */
    public void setX(double x) {
        validate(x, "x");
        double offsetX = Math.abs(x - points[0].x);
        translateX(offsetX);
    }

    /**
     * Sets the y coordinate of the point to the specified coordinate.
     *
     * @param y the new x coordinate
     * @see getY
     */
    public void setY(double y) {
        validate(y, "y");
        double offsetY = Math.abs(y - points[0].y);
        translateY(offsetY);
    }

    /**
     * Sets the width of the dimension to the specified {@code width}.
     * 
     * @param width the specified width
     * @throws ArithmeticException if the width is NaN or infinite
     * @throws IllegalArgumentException if the width is negative
     * @see getWidth
     */
    public void setWidth(double width) {
        double offset = Math.abs(width - getWidth());
        points[1].x += offset;
        points[2].x += offset;
        size.setWidth(width);
    }

    /**
     * Sets the height of the dimension to the specified {@code height}.
     * 
     * @param height the specified height
     * @throws ArithmeticException if the height is NaN or infinite
     * @throws IllegalArgumentException if the height is negative
     * @see getHeight
     */
    public void setHeight(double height) {
        double offset = Math.abs(height - getHeight());
        points[2].y += offset;
        points[3].y += offset;
        size.setHeight(height);
    }

    /**
     * Sets the bounding Rectangle of this {@code Rectangle} to the specified
     * {@code x}, {@code y}, {@code width}, and {@code height}.
     *
     * @param x      the specified x
     * @param y      the specified y
     * @param width  the specified width
     * @param height the specified height
     * @see setSize
     * @see setLocation
     */
    public void setBounds(double x, double y, double width, double height) {
        setSize(width, height);
        setLocation(x, y);
    }

    /**
     * Sets the location of the rectangle to the specified coordinates.
     *
     * @param x the new x coordinate
     * @param y the new y coordinate
     * @see getLocation()
     */
    public void setLocation(double x, double y) {
        setX(x);
        setY(y);
    }

    /**
     * Sets the size of the rectangle to the specified {@code width} and
     * {@code height}.
     *
     * @param width  the specified width
     * @param height the specified height
     * @see getSize
     */
    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    /**
     * Sets the size of the rectangle to the specified {@code width} and
     * {@code height}.
     *
     * @param width  the specified width
     * @param height the specified height
     * @see getSize
     */
    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }
}
