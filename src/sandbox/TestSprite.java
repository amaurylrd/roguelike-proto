package sandbox;

import java.awt.Graphics2D;

import engine.scene.entity.Entity;
import engine.scene.image.Sprite;

public class TestSprite extends Entity {
	public Sprite sprite;
	
	public TestSprite(double x, double y, double width, double height, int layer) {
		super(x, y, width, height, layer);
		//sprite = new Sprite();
	}
	
	
	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void draw(Graphics2D graphics) {
		// for (int i = 0; i < sprite.getFrames().length; ++i)
		// 	graphics.drawImage(sprite.frames[i], 50+i*sprite.getFrame(i).getWidth(), 200, sprite.frames[i].getWidth(), sprite.frames[i].getHeight(), null);
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isRemovable() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
