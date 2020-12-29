package engine.scene.entity;

public abstract class Player extends Entity {
	private int totalLife;
	private int currentLife; //TODO: add component LifeCounter
	//weapon : wield
	//item : hold/use

	/**
	 * Constructs a {@code Player} with a z-index set to 0.
	 * 
	 * @param x the x coordinate of the bounding {@code Rectangle}
	 * @param y the y coordinate of the bounding {@code Rectangle}
	 * @param width the width of the bounding {@code Rectangle}
	 * @param height the height of the bounding {@code Rectangle}
	 */
	public Player(double x, double y, double width, double height) {
		super(x, y, width, height, 0);
	}	
}
