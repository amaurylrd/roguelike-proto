package engine.physics2d;

public class Vector {
    private double x;
    private double y;
    
    public Vector(double x, double y) {
        set(x, y);
    }

    public void set(double x, double y) {
        setX(x);
        setY(y);
    }

    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void normalize() {
        double magnitude = getMagnitude();
        if (magnitude > 0) {
            x /= magnitude;
            y /= magnitude; 
        }
    }

    public void negate() {
        x -= x;
        y -= y;
    }

    public void scale(float factor) {
        x *= factor;
        y *= factor;
    }
}
