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
		//TODO masss
	}

	@Override
	protected void draw(Graphics2D graphics) {
		//drawBounds(graphics, Color.RED);
		graphics.fill(bounds.stroke());
		graphics.drawString("Velocity: " + velocity, (int) (bounds.getX() + bounds.getWidth()), (int) bounds.getY() - 10);
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}
