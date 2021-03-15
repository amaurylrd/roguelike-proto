package com.sandbox.collission;

import java.awt.Color;
import java.awt.Graphics2D;

import com.engine.scene.entity.Tile;

public class BoxTest extends Tile {
	public BoxTest(float x, float y, float width, float height, int zindex) {
		super("banana", x, y, width, height, zindex);
		restitution = 0.5f;
		friction = 0.01f;
	}

	public BoxTest(float x, float y, float width, float height, int zindex, boolean test) {
		super("banana", x, y, width, height, zindex);
		traversable = test;
		restitution = 0.5f;
		friction = 1;
	}

	@Override
	public void update(float dt) {
		
		
	}

	@Override
	protected void draw(Graphics2D graphics) {
		Color p = graphics.getColor();
		graphics.setColor(getLayer() ==  -3 ? Color.BLUE : Color.RED);
		graphics.fill(bounds.stroke());
		graphics.setColor(p);
		graphics.drawImage(texture, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(), (int) bounds.getHeight(), null);
	}
}
