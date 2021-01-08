package engine.scene;

import engine.geom.shape.Rectangle;
import engine.physics2d.Vector;
import engine.util.Random;

public class Camera {
    private Scene scene;
    private Vector position = new Vector(0, 0);

    public Camera(Scene scene) {
        this.scene = scene;
    }

    public double shakeDuration = 0.0;
    public double internalTimer = 0.0;
    //public int duration = 5; // In seconds, make longer if you want more variation
    public double amplitude = 20.0;
    public double frequency = 35;
    //phase ?

    public void shake(double duration) {
        shakeDuration = duration;
    }

    protected void update(double dt) {
        if (scene.player != null) {
            Rectangle playerBox = scene.player.getBounds();
            double targetX = playerBox.getX() + playerBox.getWidth() / 2 - scene.getWidth() / 2.3;
            double targetY = playerBox.getY() + playerBox.getHeight() / 2 - scene.getHeight() / 2;
            position.translate((targetX - position.getX()) * 0.1, (targetY - position.getY()) * 0.02);
        }

        internalTimer += dt;
        if (shakeDuration > 0) {
            shakeDuration -= dt;
            double shakeTime = (internalTimer * frequency);
            double deltaT = shakeTime - (int) shakeTime;
            
            //dans le constructeur
            double fst = Random.nextDouble() * 2 - 1;
            double scd = Random.nextDouble() * 2 - 1;
            double lst = Random.nextDouble() * 2 - 1;
            
            double deltaX = fst * deltaT + scd * ( 1 - deltaT);
            double deltaY = scd * deltaT + lst * ( 1 - deltaT);

            double dx = deltaX, dy = deltaY;
            position.translate(dx * amplitude * Math.min(shakeDuration, 1), dy * amplitude * Math.min(shakeDuration, 1));
        } 
    }

    protected double getX() {
        return position.getX();
    }

    protected double getY() {
        return position.getY();
    }
    
    public static double lerp(double a, double b, double f) {
		return a + f*(b - a);
	}
}