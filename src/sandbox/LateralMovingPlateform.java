package sandbox;

import engine.physics2d.Vector;
import engine.scene.entity.Tile;

public class LateralMovingPlateform extends Tile {
	public LateralMovingPlateform(double x, double y, double width, double height, int zindex) {
		super("banana", x, y, width, height, zindex);
		velocity.setX(0.1);
		type = CollisionType.KINEMATIC;
		friction = 1;
	}

	@Override
	public void update(double dt) {
		if (bounds.getX() < -300 || bounds.getX() > 50)
			velocity.scaleX(-1);
		bounds.translate(Vector.scale(velocity, dt));
	}
}
