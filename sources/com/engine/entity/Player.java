package com.engine.entity;

import com.jogamp.opengl.GL2;
import com.engine.physics2d.Vector;

public class Player extends Entity {
	public boolean grounded = false;
	
	public Vector groundVelocity = new Vector();
	
	/**
	 * Constructs a {@code Player}.
	 * 
	 * @param x      the x coordinate of the bounding {@code Rectangle}
	 * @param y      the y coordinate of the bounding {@code Rectangle}
	 * @param width  the width of the bounding {@code Rectangle}
	 * @param height the height of the bounding {@code Rectangle}
	 * @param zindex the z-index of this {@code Component}
	 */
	public Player(float x, float y, float width, float height, int zindex) {
		super(x, y, width, height, zindex);
		restitution = 0;
		friction = 0;
		im = 0.01f;
	}

	//public com.engine.geom.shape.Rectangle feet = new com.engine.geom.shape.Rectangle(bounds.getX() + bounds.getWidth() * 0.2f, bounds.getY() + bounds.getHeight(), bounds.getWidth() * 0.6f, 10f);
	
	@Override
	public void update(float dt) {
		//feet.translate(bounds.getX() + bounds.getWidth() * 0.2f - feet.getX(), bounds.getY() + bounds.getHeight() - feet.getY());
		super.update(dt);
	}
	
	// public boolean isGrounded() {
	// 	return grounded;
	// }

	@Override
	protected void draw(GL2 graphics) {
		graphics.glColor3f(0, 255, 0);
            graphics.glBegin(GL2.GL_QUADS);
            graphics.glVertex2f(bounds.getX(), bounds.getY());
            graphics.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY());
            graphics.glVertex2f(bounds.getX() + bounds.getWidth(), bounds.getY() + bounds.getHeight());
            graphics.glVertex2f(bounds.getX(), bounds.getY() + bounds.getHeight());
        graphics.glEnd();
		//graphics.fill(bounds.stroke());
		//graphics.fill(feet.stroke());
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}
