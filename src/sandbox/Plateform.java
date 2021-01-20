package sandbox;

import engine.scene.entity.Collider;

public abstract class Plateform extends Collider {
	public Plateform(double x, double y, double width, double height, int zindex) {
		super(x, y, width, height, zindex);
		type = CollisionType.KINEMATIC;
	}
}
