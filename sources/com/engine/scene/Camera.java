package com.engine.scene;

import javax.media.opengl.GL2;

import com.engine.geom.Rectangle;

/**
 * The class {@code Camera} defines a 2D camera which is following the {@code Player} movement.
 */
public class Camera implements Drawable {
    /**
	 * The {@code Scene} linked to this {@code Camera}.
	 */
    private Scene scene;

    /**
	 * The {@code Camera} translates the {@code scene} upon its {@code Player}.
	 * 
	 * @param scene the scene to capture
	 */
    public Camera(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void update(float dt) {
        if (scene.getPlayer() != null) {
			Rectangle playerBounds = scene.getPlayer().getBounds();
			//float targetX = playerBounds.getX() + playerBounds.getWidth() / 2 - scene.getWidth() / 2.3f;
			//float targetY = playerBounds.getY() + playerBounds.getHeight() / 2 - scene.getHeight() / 2;
			//translate((targetX - getX()) * 0.1f, (targetY - getY()) * 0.02f);
		}
    }

    @Override
    public void render(GL2 graphics) {
        // TODO Auto-generated method stub
        
    }
    
}
