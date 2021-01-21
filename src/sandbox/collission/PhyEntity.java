package sandbox.collission;

import java.awt.Graphics2D;

import engine.physics2d.Vector;
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
	public PhyEntity(double x, double y, double width, double height, int zindex) {
		super(x, y, width, height, zindex);
	}

	public PhyEntity(double x, double y, double width, double height, int zindex, boolean b) {
		super(x, y, width, height, zindex);
		type = CollisionType.KINEMATIC;
	}

	@Override
	public void update(double dt) {
		bounds.translate(Vector.scale(velocity, dt));
	}

	@Override
	protected void draw(Graphics2D graphics) {
		drawBounds(graphics, java.awt.Color.RED);
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
}