package com.sandbox;

import com.engine.physics2d.Vector;
import com.engine.scene.entity.Tile;

public class VerticalMovingPlateform extends Tile {
	public VerticalMovingPlateform(float x, float y, float width, float height, int zindex) {
		super("banana", x, y, width, height, zindex);
		velocity.setY(-0.1f);
		type = CollisionType.KINEMATIC;
	}

	@Override
	public void update(float dt) {
		if (bounds.getY() > 600 || bounds.getY() < 200)
			velocity.scaleY(-1);
		bounds.translate(Vector.scale(velocity, dt));
	}
}
