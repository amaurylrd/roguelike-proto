package engine.scene.entity;

import java.awt.Graphics2D;
import java.awt.Color;

public class Player extends Entity { //abstract player à check instance of extends
	//private int totalLife;
	//private int currentLife; TODO: add component LifeCounter
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
		restitution = 0.0;
		friction = 0.0;
		//TODO masss
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
