package sandbox;

import engine.physics2d.Vector;
import engine.scene.entity.Tile;

public class VerticalMovingPlateform extends Tile {
	Vector opos;
	
	public VerticalMovingPlateform(double x, double y, double width, double height, int zindex) {
		super("banana", x, y, width, height, zindex);
		velocity.setY(-0.1);
		type = CollisionType.KINEMATIC;
	}

	@Override
	public void update(double dt) {
		if (bounds.getY() > 600 || bounds.getY() < 200)
			velocity.scaleY(-1);
		bounds.translate(Vector.scale(velocity, dt));
	}
}
