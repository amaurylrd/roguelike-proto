package engine.scene;

import engine.geom.shape.Rectangle;
import engine.physics2d.Vector;
import engine.scene.entity.Player;

public class Camera {
    private Player target;
    private Vector offset;

    public Camera(Player player) {
        target = player;
        offset = new Vector(0, 0);
        if (player != null) {
            Rectangle bounds = player.getBounds();
            float xoff = (float) (bounds.getX() + bounds.getWidth() / 2);
            float yoff = (float) (bounds.getY() + bounds.getHeight() /2);
            offset.set(xoff, yoff);
        }
    }

    protected float getX() {
        return offset.getX();
    }

    protected float getY() {
        return offset.getY();
    }

    protected void update(float dt) {
        if (target != null) {
            float xoff = lerp(offset.getX(), target.getBounds().getX(), dt);
            float yoff = lerp(offset.getY(), target.getBounds().getY(), dt);
            offset = new Vector(xoff, yoff);
        }
    }
    
    public static float lerp(float a, float b, float f) {
		return a + f*(b - a);
	}
}