package com.game.level;

import java.awt.Graphics2D;
import java.awt.Color;
import com.engine.scene.entity.Tile;

public class TileTest extends Tile {
	public TileTest(String name, float x, float y, float width, float height, int layer) {
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
