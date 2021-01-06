package engine.scene.entity;

public abstract class Collider extends Component implements Collidable {
	protected double bounciness;
	protected double friction;

	public Collider(double x, double y, double width, double height, int layer) {
		super(x, y, width, height, layer);
	}
}
