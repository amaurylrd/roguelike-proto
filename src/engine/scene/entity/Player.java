package engine.scene.entity;

import java.awt.Graphics2D;
import java.awt.Color;

public class Player extends Entity {
	private int totalLife;
	private int currentLife; // TODO: add component LifeCounter
	// weapon : wield
	// item : hold/use

	/**
	 * Constructs a {@code Player} with a z-index set to 0.
	 * 
	 * @param x      the x coordinate of the bounding {@code Rectangle}
	 * @param y      the y coordinate of the bounding {@code Rectangle}
	 * @param width  the width of the bounding {@code Rectangle}
	 * @param height the height of the bounding {@code Rectangle}
	 */
	public Player(double x, double y, double width, double height) {
		super(x, y, width, height, 0);
		solid = true;
	}

	@Override
	public void update(float dt) {
		bounds.translate(velocity.scale((double) dt));
	}

	@Override
	protected void draw(Graphics2D graphics) {
		graphics.setColor(Color.GREEN);
		graphics.draw(bounds.stroke());
	}

	@Override
	public boolean isRemovable() {
		return false;
	}

	@Override
	public Player clone() {
		Player p = new Player(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
		p.velocity = velocity.clone();
		return p;
	}
}
