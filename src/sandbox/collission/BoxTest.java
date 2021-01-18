package sandbox.collission;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.scene.entity.Tile;

public class BoxTest extends Tile {

	public BoxTest(double x, double y, double width, double height, int zindex) {
		super("banana", x, y, width, height, zindex);
		restitution = 0.5;
		friction = 0.01;
	}

	public BoxTest(double x, double y, double width, double height, int zindex, boolean test) {
		super("banana", x, y, width, height, zindex);
		traversable = test;
		restitution = 0.5;
		friction = 0.01;
	}

	@Override
	public void update(double dt) {
		
		
	}

	@Override
	protected void draw(Graphics2D graphics) {
		//drawBounds(graphics, Color.RED);
		
		
		if (getLayer() ==  -3) { //TODO TEST
			
			//engine.scene.Scene.lock = true;
			Color p = graphics.getColor();
			graphics.setColor(Color.BLUE);
			graphics.fill(bounds.stroke());
			graphics.setColor(p);
			//engine.scene.Scene.lock = false;
		} else {
			//engine.scene.Scene.lock = true;
			Color penColor = graphics.getColor();
        	graphics.setColor(Color.RED);
        	graphics.draw(bounds.stroke());
			graphics.setColor(penColor);
			//engine.scene.Scene.lock = false;
		}
		//graphics.drawImage(texture, (int) bounds.getX(), (int) bounds.getY(), null)
	}
}
