package engine.scene;

import engine.geom.shape.Rectangle;
import engine.physics2d.Vector;
import engine.scene.entity.Player;

public class Camera {
    private Scene scene;

    //offset transform
    private Vector position = new Vector(0, 0);

    public Camera(Scene scene) {
        this.scene = scene;
    }

    protected void update(float dt) {
        if (scene.player != null) {
            Rectangle bounds = scene.player.getBounds();
            double xoff = bounds.getX() + bounds.getWidth()/2 - scene.getWidth()/2.2;
            double yoff = bounds.getY() + bounds.getHeight()/2 - scene.getHeight()/2.2;
            position.translateX((xoff - position.getX()) * 0.1);
            position.translateY((yoff - position.getY()) * 0.02);
            //offset.translate(xoff, yoff);
            //offset.set(lerp(offset.getX(), xoff, dt), lerp(offset.getY(), yoff, dt));
        }
    }

    protected double getX() {
        return position.getX();
    }

    protected double getY() {
        return position.getY();
    }

    // protected void update(float dt) {
    //     if (target != null) {
    //         float xoff = lerp(offset.getX(), target.getBounds().getX(), dt);
    //         float yoff = lerp(offset.getY(), target.getBounds().getY(), dt);
    //         offset = new Vector(xoff, yoff);
    //     }
    // }
    
    public static double lerp(double a, double b, double f) {
		return a + f*(b - a);
	}
}