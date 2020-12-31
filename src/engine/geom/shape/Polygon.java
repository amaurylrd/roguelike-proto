package engine.geom.shape;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * This class represents a polygon (should be convex and closed).
 */
public class Polygon extends Shape {
	/**
     * The vertices of this {@code Polygon}.
     */
	protected Point2D.Double[] points;

	/**
	 * The number of vertices in this {@code Polygon}.
	 */
	protected int vertices;

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
		vertices = Math.min(x.length, y.length);
		points = new Point2D.Double[vertices];
		for (int i = 0; i < points.length; ++i)
			points[i] = new Point2D.Double(x[i], y[i]);
	}

	/**
     * Checks the coordinates and throws an {@code Exception} if a value is either <i>NaN</i>, infinite or <i>null</i>.
     * 
     * @param coordinates the specified coordinates
     * @param name the name of the variables
     * @throws NullPointerException unless {@code coordinates[]} is not <i>null</i>
     * @throws ArithmeticException if any coordinate is either <i>NaN</i> or infinite
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
     * Checks the specified coordinate and throws an {@code Exception} if a value is either <i>NaN</i> or infinite.
     * 
     * @param coordinate the specified coordinate
     * @param name the name of the variable
     * @throws ArithmeticException if {@code coordonate} is either <i>NaN</i> or infinite
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
     * @return <i>true</i> if the point (x, y) is inside this {@code Polygon}, <i>false</i> otherwise
     */
	@Override
	public boolean contains(double x, double y) {
		if (vertices > 2) {
			boolean pos = false, neg = false;
			for (int i = 0; i < vertices; ++i) {
				double px = points[i].x, py = points[i].y;
				if (px == x && py == y)
					return true;

				int j = (i + 1)%vertices;
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
	 * Checks whenever two polygons intersect.
	 * It considers overlapping not an insertection.
	 * 
	 * @param polygon the second polygon
	 * @return <i>true</i> if the {@code polygon} intersects this {@code Polygon}, <i>false</i> otherwise
	 */
	public boolean intersects(Polygon polygon) {
		for (int i = 0; i < vertices; ++i) {
			int ii = (i + 1)%vertices;
			Point2D.Double[] edge = new Point2D.Double[] {points[i], points[ii]};
			for (int j = 0; j < polygon.vertices; ++j) {
				int jj = (j + 1)%polygon.vertices;
				Point2D.Double[] edge1 = new Point2D.Double[] {polygon.points[j], polygon.points[jj]};
				
				double a = edge[1].y - edge[0].y;
				double b = edge[0].x - edge[1].x;
				double c = a*edge[0].x + b*edge[0].y;
				double a1 = edge1[1].y - edge1[0].y;
				double b1 = edge1[0].x - edge1[1].x;
				double c1 = a1*edge1[0].x + b1*edge1[0].y;

				double determinant = a*b1 - a1*b;
				final double ACCURACY = 0.000001;
				if (determinant >= ACCURACY) {
					double x = (b1*c - b*c1)/determinant;
					double y = (a*c1 - a1*c)/determinant;
					
					if (inside(x, edge[0].x, edge[1].x) && inside(x, edge1[0].x, edge1[1].x)
						&& inside(y, edge[0].y, edge[1].y) && inside(y, edge1[0].y, edge1[1].y))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if {@code x} is inside {@code a} and {@code b}.
	 * 
	 * @param x the specicifed value to verify
	 * @param a the first number
	 * @param b the second number
	 * @return <i>true</i> if {@code x} is inside the first and second number, <i>false</i> otherwise
	 */
	private boolean inside(double x, double a, double b) {
		if (a < b)
			return a <= x && x <= b;
		return b < a ? a >= x && x >= b : a == x;
	}
	
	/**
	 * Gives the area of this {@code Polygon}.
	 * 
	 * @return the area
	 */
	@Override
	public double area() {
		double area = 0.0;
		for (int i = 0; i < vertices; ++i) {
			int j = (i + 1)%vertices;
			area += points[i].x*points[j].y;
			area -= points[i].y*points[j].x;
		}
		return Math.abs(area) / 2;
	}

	/**
     * Translates this point, at location (x, y) by {@code dx} along the X axis.
	 * Post-translation the point (x, y) becomes (x+dx, y)
     *
     * @param dx the x coordinate to be added
	 * @throws ArithmeticException if {@code dx} is either <i>NaN</i> or infinite
     */
	@Override
	public void translateX(double dx) {
		validate(dx, "dx");
		for (int i = 0; i < vertices; ++i)
			points[i].x += dx;
	}

	/**
     * Translates this point, at location (x, y) by {@code dy} along the Y axis.
	 *	Post-translation the point (x, y) becomes (x, y+dy)
	 *
     * @param dy the y coordinate to be added
	 * @throws ArithmeticException if {@code dy} is either <i>NaN</i> or infinite
     */
	@Override
	public void translateY(double dy) {
		validate(dy, "dy");
		for (int i = 0; i < vertices; ++i)
			points[i].y += dy;
	}

	/**
     * Returns the mass center of this {@code Polygon}.
     * 
     * @return the center coordonates
     */
	public Point2D.Double centroid() {
		double tmp, determinant = 0.0;
		double centroidX = 0.0, centroidY = 0.0;
		for (int i = 0; i < vertices; i++) {
			int j = (i + 1)%vertices;
			tmp = points[i].x * points[j].y - points[j].x*points[i].y;

			centroidX += (points[i].x + points[j].x)*tmp;
			centroidY += (points[i].y + points[j].y)*tmp;
			determinant += tmp;
		}
		centroidX /= 3*determinant;
		centroidY /= 3*determinant;
		return new Point2D.Double(centroidX, centroidY);
		
	}

	/**
     * Returns the graphical center of this {@code Polygon}.
     * 
     * @return the center coordonates
     */
	@Override
	public Point2D.Double center() {
		double centerX = 0.0, centerY = 0.0;
		for (int i = 0; i < vertices; ++i) {
			centerX += points[i].x;
			centerY += points[i].y;
		}
		centerX /= vertices;
		centerY /= vertices;
		return new Point2D.Double(centerX, centerY);
	}

	/**
     * Rotates the Z axis of this {@code Polygon} in counter-clockwise direction.
     * The point (x, y) becomes (xcosθ−ysinθ, xsinθ+ycosθ).
     * 
     * @param theta the angle of rotation in radians
     */
	@Override
	public void rotate(float theta) {
		rotation -= theta;
		Point2D.Double massCenter = centroid();
		for (int i = 0; i < vertices; i++) {
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
	 * Returns the path of this {@code Shape} for draw calls.
	 * 
	 * @return the path of this {@code Polygon}
	 */
	@Override
	public java.awt.Shape stroke() {
		Path2D path = new Path2D.Double();
		path.moveTo(points[0].x, points[0].y);
        for (int i = 1; i < vertices; ++i)
            path.lineTo(points[i].x, points[i].y);
        path.closePath();
        return path;
	}

	/**
	 * Returns the representation of this {@code Shape} as {@code String}.
	 * 
	 * @return the {@code String} representing this object
	 */
	@Override
	public String toString() {
		String out = this.getClass().getName() + "[";
		for (int i = 0; i < vertices; ++i)
			out += points[i];
		return out + "]";
	}
}
