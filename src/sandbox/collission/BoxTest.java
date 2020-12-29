package sandbox.collission;

import java.awt.Color;
import java.awt.Graphics2D;

import engine.scene.entity.Collidable;
import engine.scene.entity.Entity;

public class BoxTest extends Entity {

	public BoxTest(double x, double y, double width, double height) {
		super(x, y, width, height, 0);
		setSolid(true);
	}

	@Override
	public void update(float dt) {
	}

	@Override
	protected void draw(Graphics2D graphics) {
		graphics.setColor(Color.RED);
		graphics.draw(bounds.stroke());
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
