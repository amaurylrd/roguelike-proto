package sandbox.collission;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.scene.entity.Collidable;
import engine.scene.entity.Tile;

public class BoxTest extends Tile {

	public BoxTest(double x, double y, double width, double height, int layer) {
		super("banana", x, y, width, height, layer);
		solid = true;
	}

	public BoxTest(double x, double y, double width, double height, int layer, boolean test) {
		super("banana", x, y, width, height, layer);
		solid = true;
		TESTBAS = test;
	}

	@Override
	public void update(float dt) {
		
		
	}

	@Override
	protected void draw(Graphics2D graphics) {
		drawBounds(graphics, Color.RED);
		
		if (getLayer() ==  -3) { //TODO TEST
			
			Color p = graphics.getColor();
			graphics.setColor(Color.BLUE);
			graphics.fill(bounds.stroke());
			graphics.setColor(p);
		}
		//graphics.drawImage(texture, (int) bounds.getX(), (int) bounds.getY(), null);
		
		if (TEST)
			graphics.fill(bounds.stroke());
	}

	@Override
	public boolean isRemovable() {
		return false;
	}

	@Override
	public boolean collides(Collidable component) {
		// TODO Auto-generated method stub
		return false;
	}
}
