package engine.geom.shape;

public interface Shape {
    public boolean contains(double x, double y);

    /**
     * Translates the polygon by the specified distance, along the X
     * coordinate axis, and downward along the Y coordinate axis.
     *
     * @param dx the distance to move along the X axis
     * @param dy the distance to move along the Y axis
     */
    public default void translate(double dx, double dy) {
        translateX(dx);
        translateY(dy);
    }

    public void translateX(double dx);

    public void translateY(double dy);

    public void rotate(double theta);

    public java.awt.Shape stroke();
}
