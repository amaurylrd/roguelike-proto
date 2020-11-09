package engine.geom;

public class Veclocity {
    private double[] vector;
    
    public Veclocity(double xpseed, double yspeed) {
        vector = new double[] {xpseed, xpseed};
    }

    public double getX() {
        return vector[0];
    }

    public void setX(double xpseed) {
        vector[0] = xpseed;
    }

    public double getY() {
        return vector[1];
    }

    public void setY(double yspeed) {
        vector[1] = yspeed;
    }

    public void setSpeed(double xpseed, double yspeed) {
        setX(xpseed);
        setY(yspeed);
    }
}
