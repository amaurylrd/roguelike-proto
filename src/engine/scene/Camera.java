package engine.scene;

import engine.geom.shape.Rectangle;
import engine.physics2d.Vector;
import engine.scene.entity.Player;

public class Camera {
    private Scene scene;
    private Vector offset = new Vector(0, 0);

    public Camera(Scene scene) {
        this.scene = scene;
    }

    protected void update(float dt) {
        if (scene.player != null) {
            Rectangle bounds = scene.player.getBounds();
            double xoff = bounds.getX() + bounds.getWidth()/2 - scene.getWidth()/2.2;
            double yoff = bounds.getY() + bounds.getHeight()/2 - scene.getHeight()/2.2;
             xoff += (xoff - offset.getX()) * 0.1;
             yoff += (yoff - offset.getX()) * 0.1;
            offset.set(xoff, yoff);
            //offset.set(lerp(offset.getX(), xoff, dt), lerp(offset.getY(), yoff, dt));
        }
    }

    protected double getX() {
        return offset.getX();
    }

    protected double getY() {
        return offset.getY();
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