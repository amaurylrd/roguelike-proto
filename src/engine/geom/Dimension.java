package engine.geom;

/**
 * The {@code Dimension} class encapsulates the width and height of a component.
 */
public class Dimension {
    /**
     * The width dimension. (negative values can't be used)
     */
    protected double width;

    /**
     * The height dimension. (negative values can't be used)
     */
    protected double height;
    
    /**
     * Constructs a {@code Dimension} and initializes it to the specified width and specified height.
     * 
     * @param width the specified width
     * @param height the specified height
     * @see setSize(double width, double height)
     */
    public Dimension(double width, double height) {
        setSize(width, height);
    }

    /**
     * Throws an <code>Exception</code> if the specified value is either NaN, infinite or negative.
     * 
     * @param value the specified value
     * @param name the name of the variable
     * @throws IllegalArgumentException if {@code value} is negative
     * @throws ArithmeticException if {@code value} is either NaN or infinite
     */
    private void validate(double value, String name) {
        if (Double.isNaN(value))
            throw new ArithmeticException("Illegal double value : " + name + " is NaN.");
        if (Double.isInfinite(value))
            throw new ArithmeticException("Illegal double value : " + name + " is infinite.");
        if (value < 0.0)
            throw new IllegalArgumentException("Illegal double value : " + name + " is negative.");
    }

    /**
     * Returns the width of the dimension.
     * 
     * @return the width of the dimension
     * @see setWidth(double width)
     */
    public double getWidth() {
        return width;
    }

     /**
     * Returns the height of the dimension.
     * 
     * @return the height of the dimension
     * @see setHeight(double height)
     */
    public double getHeight() {
        return height;
    }

    /**
     * Sets the width of the dimension to the specified width.
     * 
     * @param width the specified width
     * @throws ArithmeticException if the width is NaN or infinite
     * @throws IllegalArgumentException if the width is negative
     * @see getWidth
     * @see validate
     */
    public void setWidth(double width) {
        validate(width, "width");
        this.width = width;
    }

    /**
     * Sets the height of the dimension to the specified height.
     * 
     * @param height the specified height
     * @throws ArithmeticException if the height is NaN or infinite
     * @throws IllegalArgumentException if the height is negative
     * @see getHeight
     * @see validate
     */
    public void setHeight(double height) {
        validate(height, "height");
        this.height = height;
    }

    /**
     * Sets the size of the dimension to the specified width and height.
     * 
     * @param width the specified width
     * @param height the specified height
     * @see getSize
     * @see setWidth
     * @see setHeight
     */
    public void setSize(double width, double height) {
        setWidth(width);
        setHeight(height);
    }

    /** 
     * Gets the size of the dimension.
     *
     * @return a new instance of <code>Dimension2D</code> with the same width and height
     */
    public Dimension2D getSize() {
        return new Dimension2D(width, height);
    }

    /**
     * Returns a string representation of the values of this <code>Dimension2D</code> object's height and width fields.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName()+"[width:"+width+", height:"+height+"]";
    }
}