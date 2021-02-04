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
	public Player(double x, double y, double width, double height, int zindex) {
		super(x, y, width, height, zindex);
		restitution = 0.0;
		friction = 0.0;
		im = 0.01;
	}

	public engine.geom.shape.Rectangle feet = new engine.geom.shape.Rectangle(bounds.getX() + bounds.getWidth() * 0.2, bounds.getY() + bounds.getHeight(), bounds.getWidth() * 0.6, 10);
	
	@Override
	public void update(double dt) {
		feet.translate(bounds.getX() + bounds.getWidth() * 0.2 - feet.getX(), bounds.getY() + bounds.getHeight() - feet.getY());
		super.update(dt);
	}
	
	

	public boolean grounded = false;
	public double groundedVelocityX = 0.0;
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
