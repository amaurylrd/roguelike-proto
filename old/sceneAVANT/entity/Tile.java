package com.engine.scene.entity;

import java.awt.Graphics2D;

import com.engine.application.Ressources;
import com.engine.physics2d.Collider;

public abstract class Tile extends Collider {

	public Tile(String ressourceName, float x, float y, float width, float height, int zindex) {
		super(x, y, width, height, zindex);
		texture = Ressources.ressource(ressourceName);
	}

	@Override
	protected void draw(Graphics2D graphics) {
		graphics.drawImage(texture, (int) bounds.getX(), (int) bounds.getY(), (int) bounds.getWidth(),
				(int) bounds.getHeight(), null);
	}

	/**
	 * Checks if this {@code Component} could be remove.
	 * 
	 * @return <i>true</i> if this {@code Component} has to be remove from draw
	 *         calls, <i>false</i> otherwise
	 */
	@Override
	public boolean isRemovable() {
		return false;
	}
}
