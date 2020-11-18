package engine.geom.shape;

import java.awt.geom.Path2D;

//Convex and Closed
public class Polygon implements Shape {
	protected class Point {
		public double x;
		public double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	/**
     * The vertices of this {@code Polygon}.
     */
	protected Point[] points;

	/**
	 * The number of vertices in this {@code Polygon}.
	 */
	protected int npoints;

	/**
     * The rotation in radians applied to this {@code Polygon}. 
     */
	protected double rotation = 0;

	/**
     * Constructs and initializes a {@code Polygon} from the specified parameters.
     * 
     * @param x the array of x coordinates
     * @param y the array of y coordinates
     * @throws ArithmeticException if any coordinate is either NaN or infinite
     * @throws NullPointerException if {@code x} or {@code y} is null
     * @see validate(double[] coordonates, String name)
     */
	public Polygon(double[] x, double[] y) {
		validate(x, "x");
		validate(y, "y");
		npoints = Math.min(x.length, y.length);
		points = new Point[npoints];
		for (int i = 0; i < points.length; ++i)
			points[i] = new Point(x[i], y[i]);
	}

	/**
     * Checks the coordinates and throws an {@code Exception} if a value is either NaN, infinite or null.
     * 
     * @param coordinates the specified coordinates
     * @param name the name of the variables
     * @throws NullPointerException unless {@code points[]} is not null
     * @throws ArithmeticException if any coordinate is either NaN or infinite
     */
	protected void validate(double[] coordonates, String name) {
		if (coordonates == null || coordonates.length < 1)
			throw new NullPointerException();
		for (int i = 0; i < coordonates.length; i++) {
			if (Double.isNaN(coordonates[i]))
				throw new ArithmeticException("Illegal double value : " + name + " coordinate " + i + " is NaN.");
			if (Double.isInfinite(coordonates[i]))
				throw new ArithmeticException("Illegal double value : " + name + " coordinate " + i + " is infinite.");
		}
	}

	/**
     * Checks the specified coordinate and throws an {@code Exception} if a value is either NaN or infinite.
     * 
     * @param coordinate the specified coordinate
     * @param name the name of the variable
     * @throws ArithmeticException if {@code coordonate} is either NaN or infinite
     */
	protected void validate(double coordonate, String name) {
		if (Double.isNaN(coordonate))
			throw new ArithmeticException("Illegal double value : " + name + " is NaN.");
		if (Double.isInfinite(coordonate))
			throw new ArithmeticException("Illegal double value : " + name + " is infinite.");
	}

	/**
     * Checks whether or not this {@code Polygon} contains the point at the specified location (x, y).
     *
     * @param x the specified x coordinate
     * @param y the specified y coordinate
     * @return <i>true</i> if the point (x, y) is inside this {@code Polygon}, <i>false</i> otherwise.
     */
	@Override
	public boolean contains(double x, double y) {
		if (npoints > 2) {
			boolean pos = false, neg = false;
			for (int i = 0; i < npoints; ++i) {
				double px = points[i].x, py = points[i].y;
				if (px == x && py == y)
					return true;

				int j = i + 1 != npoints ? i + 1 : 0;
				double crossProduct = (x - px)*(points[j].y - py) - (y - py)*(points[j].x - px);
				if (crossProduct > 0)
					pos = true;
				else if (crossProduct < 0)
					neg = true; 
				if (pos && neg)
					return false;
			}
			return true;
		}
		return false;
	}

	/**
     * Translates this point, at location (x, y) by {@code dx} along the x axis.
     *
     * @param dx the x coordinate to add
     */
	@Override
	public void translateX(double dx) {
		validate(dx, "dx");
		for (int i = 0; i < npoints; ++i)
			points[i].x += dx;
	}

	/**
     * Translates this point, at location (x, y) by {@code dy} along the y axis.
     *
     * @param dy the y coordinate to add
     */
	@Override
	public void translateY(double dy) {
		validate(dy, "dy");
		for (int i = 0; i < npoints; ++i)
			points[i].y += dy;
	}

	/**
     * Returns the mass center of this {@code Polygon}.
     * 
     * @return the center coordonates
     */
	public Point centroid() {
		double tmp, determinant = 0.0;
		double centroidX = 0.0, centroidY = 0.0;
		for (int i = 0; i < npoints; i++) {
			int j = i + 1 != npoints ? i + 1 : 0;
			tmp = points[i].x * points[j].y - points[j].x*points[i].y;

			centroidX += (points[i].x + points[j].x)*tmp;
			centroidY += (points[i].y + points[j].y)*tmp;
			determinant += tmp;
		}
		centroidX /= 3*determinant;
		centroidY /= 3*determinant;
		return new Point(centroidX, centroidY);
		
	}

	/**
     * Returns the graphical center of this {@code Polygon}.
     * 
     * @return the center coordonates
     */
	public Point center() {
		double centerX = 0.0, centerY = 0.0;
		for (int i = 0; i < npoints; ++i) {
			centerX += points[i].x;
			centerY += points[i].y;
		}
		centerX /= npoints;
		centerY /= npoints;
		return new Point(centerX, centerY);
	}

	/**
     * Rotates the Z axis of this {@code Polygon} in counter-clockwise direction.
     * The point (x, y) becomes (xcosθ−ysinθ, xsinθ+ycosθ).
     * 
     * @param theta the angle of rotation in radians
     */
	@Override
	public void rotate(double theta) {
		rotation -= theta;
		Point massCenter = centroid();
		for (int i = 0; i < npoints; i++) {
			double centerX = massCenter.x, centerY = massCenter.y;
			points[i].x -= centerX;
			points[i].y -= centerY;
			double deltaX = points[i].x*Math.cos(rotation) - points[i].y*Math.sin(rotation);
			double deltaY = points[i].x*Math.sin(rotation) + points[i].y*Math.cos(rotation);
			points[i].x = deltaX + centerX;
            points[i].y = deltaY + centerY;
		}
	}

	/**
     * Gets the rotation of this {@code Rectangle} in radians.
     * 
     * @return the rotation theta
     */
    public double getRotation() {
        return rotation;
    }

	/**
	 * Returns the path of this {@code Shape} for draw calls.
	 * 
	 * @return the path of this {@code Polygon}
	 */
	@Override
	public java.awt.Shape stroke() {
		Path2D path = new Path2D.Double();
		path.moveTo(points[0].x, points[0].y);
        for (int i = 1; i < npoints; ++i)
                path.lineTo(points[i].x, points[i].y);
        path.closePath();
        return path;
	}
}
