package engine.scene;

import engine.geom.shape.Rectangle;
import engine.physics2d.Vector;
import engine.scene.entity.Player;

public class Camera {
    private Scene scene;
    private Vector position = new Vector(0, 0);

    public Camera(Scene scene) {
        this.scene = scene;
    }

    protected void update(float dt) {
        if (scene.player != null) {
            Rectangle playerBox = scene.player.getBounds();
            double targetX = playerBox.getX() + playerBox.getWidth()/2 - scene.getWidth()/2.2;
            double targetY = playerBox.getY() + playerBox.getHeight()/2 - scene.getHeight()/2.2;
            position.translate((targetX - position.getX()) * 0.1, (targetY - position.getY()) * 0.02);
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