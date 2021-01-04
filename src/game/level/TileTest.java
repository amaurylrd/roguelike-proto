package game.level;

import java.awt.Graphics2D;
import java.awt.Color;
import engine.scene.entity.Tile;

public class TileTest extends Tile {
	public TileTest(String name, double x, double y, double width, double height, int layer) {
		super(name, x, y, width, height, layer);
		solid = true;
	}

	@Override
	public void update(float dt) {}

	@Override
	protected void draw(Graphics2D graphics) {
		drawBounds(graphics, Color.RED);
	}
}
