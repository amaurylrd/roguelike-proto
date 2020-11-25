package engine.scene.entity;

public abstract class Player extends Entity {
	//z-index should be 0 as centered on player
	public Player(double x, double y, double width, double height) {
		super(x, y, width, height);
	}
}
