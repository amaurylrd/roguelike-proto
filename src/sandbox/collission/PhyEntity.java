package sandbox.collission; 

import java.awt.Graphics2D;

import engine.scene.entity.Entity;

public class PhyEntity extends Entity {
	/**
	 * Constructs a {@code Player} with a z-index set to 0.
	 * 
	 * @param x      the x coordinate of the bounding {@code Rectangle}
	 * @param y      the y coordinate of the bounding {@code Rectangle}
	 * @param width  the width of the bounding {@code Rectangle}
	 * @param height the height of the bounding {@code Rectangle}
	 */
	public PhyEntity(double x, double y, double width, double height) {
		super(x, y, width, height, 0);
	}

	@Override
	public void update(float dt) {
		bounds.translate(velocity.scale((double) dt));
	}

	@Override
	protected void draw(Graphics2D graphics) {
		graphics.setColor(java.awt.Color.MAGENTA);
		graphics.draw(bounds.stroke());
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}