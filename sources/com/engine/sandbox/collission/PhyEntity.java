package com.engine.sandbox.collission;

import java.awt.Graphics2D;

import com.engine.physics2d.Vector;
import com.engine.scene.entity.Entity;

public class PhyEntity extends Entity {
	/**
	 * Constructs a {@code Player} with a z-index set to 0.
	 * 
	 * @param x      the x coordinate of the bounding {@code Rectangle}
	 * @param y      the y coordinate of the bounding {@code Rectangle}
	 * @param width  the width of the bounding {@code Rectangle}
	 * @param height the height of the bounding {@code Rectangle}
	 */
	public PhyEntity(float x, float y, float width, float height, int zindex) {
		super(x, y, width, height, zindex);
		im = 0.02f;
	}

	public PhyEntity(float x, float y, float width, float height, int zindex, boolean b) {
		super(x, y, width, height, zindex);
		type = CollisionType.KINEMATIC;
		im = 0;
	}

	@Override
	public void update(float dt) {
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