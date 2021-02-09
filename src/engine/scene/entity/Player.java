package engine.scene.entity;

import java.awt.Graphics2D;

public class Player extends Entity { //abstract player à check instance of extends
	//private int totalLife;
	//private int currentLife; TODO: add component LifeCounter
	// weapon : wield
	// item : hold/use

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

	public engine.geom.shape.Rectangle feet = new engine.geom.shape.Rectangle(bounds.getX() + bounds.getWidth() * 0.2f, bounds.getY() + bounds.getHeight(), bounds.getWidth() * 0.6f, 10f);
	
	@Override
	public void update(float dt) {
		feet.translate(bounds.getX() + bounds.getWidth() * 0.2f - feet.getX(), bounds.getY() + bounds.getHeight() - feet.getY());
		super.update(dt);
	}
	
	

	public boolean grounded = false;
	public float groundedVelocityX = 0;
	// public boolean isGrounded() {
	// 	return grounded;
	// }

	@Override
	protected void draw(Graphics2D graphics) {
		graphics.fill(bounds.stroke());
		graphics.fill(feet.stroke());
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}
