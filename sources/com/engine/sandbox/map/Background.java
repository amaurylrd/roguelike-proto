package com.sandbox.map;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.engine.scene.entity.Component;
import com.engine.application.Ressources;

public class Background extends Component {

	public Background(float x, float y, float width, float height, int layer) {
		super(x, y, width, height, layer);
	}

	@Override
	public void update(float dt) {}

	@Override
	protected void draw(Graphics2D graphics) {
		BufferedImage image = Ressources.ressource("underground");
		graphics.drawImage(image, 0, 0, 1920*2, 1080*2, null);

	}

	@Override
	public boolean isRemovable() {
		return false;
	}
	
	
}
