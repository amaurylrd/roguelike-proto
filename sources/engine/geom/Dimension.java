package engine.geom;

/**
 * The {@code Dimension} class encapsulates the width and height of a component.
 * Negative width and height can't be used.
 */
public class Dimension {
    /**
     * The width (negative values can't be used).
     */
    public float width;

    /**
     * The height (negative values can't be used).
     */
    public float height;

    /**
     * Constructs a {@code Dimension} and initializes it to the specified {@code width} and {@code height}.
     * 
     * @param width the specified width
     * @param height the specified height
     * @see setSize
     */
    public Dimension(float width, float height) {
        setSize(width, height);
    }
    
    public Dimension(java.awt.Dimension size) {
        setSize((float) size.width, (float) size.height);
    }

    /**
     * Throws an {@code Exception} if the specified value is either <i>NaN</i>, infinite or negative.
     * 
     * @param value the specified value
     * @param name the name of the variable
     * @throws IllegalArgumentException if {@code value} is negative
     * @throws ArithmeticException if {@code value} is either NaN or infinite
     */
    private void validate(float value, String name) {
        if (Float.isNaN(value))
            throw new ArithmeticException("Illegal float value : " + name + " is NaN.");
        if (Float.isInfinite(value))
            throw new ArithmeticException("Illegal float value : " + name + " is infinite.");
        if (value < 0)
            throw new IllegalArgumentException("Illegal float value : " + name + " is negative.");
    }

    /**
     * Returns the width of the dimension.
     * 
     * @return the width of the dimension
     * @see setWidth(float width)
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height of the dimension.
     * 
     * @return the height of the dimension
     * @see setHeight(float height)
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the width of the dimension to the specified {@code width}.
     * 
     * @param width the specified width
     * @throws ArithmeticException if the width is NaN or infinite
     * @throws IllegalArgumentException if the width is negative
     * @see getWidth()
     * @see validate(float value, String name)
     */
    public void setWidth(float width) {
        validate(width, "width");
        this.width = width;
    }

    /**
     * Sets the height of the dimension to the specified {@code height}.
     * 
     * @param height the specified height
     * @throws ArithmeticException if the height is NaN or infinite
     * @throws IllegalArgumentException if the height is negative
     * @see getHeight()
     * @see validate(float value, String name)
     */
    public void setHeight(float height) {
        validate(height, "height");
        this.height = height;
    }

    /**
     * Sets the size of the dimension to the specified {@code width} and {@code height}.
     * 
     * @param width the specified width
     * @param height the specified height
     * @see getSize()
     * @see setWidth(float width)
     * @see setHeight(float height)
     */
    public void setSize(float width, float height) {
        setWidth(width);
        setHeight(height);
    }

    /** 
     * Gets the size of the dimension.
     *
     * @return a new instance of {@code Dimension} with the same width and height
     */
    public Dimension getSize() {
        return this.clone();
    }

    /**
     * Creates and returns a copy of this object.
     */
    @Override
    public Dimension clone() {
        return new Dimension(getWidth(), getHeight());
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
        if (!(object instanceof Dimension))
            return false;
        Dimension dimension = (Dimension) object;
        return getWidth() == dimension.getWidth() && getHeight() == dimension.getHeight();
    }

    /**
     * Returns a string representation of the values of this {@code Dimension} object's height and width fields.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "[width: " + getWidth() + ", height: " + getHeight() + "]";
    }
}