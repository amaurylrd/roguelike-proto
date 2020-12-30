package sandbox.collission;

import java.awt.Graphics2D;
import java.awt.Color;
import engine.scene.entity.Player;

public class PlayerTest extends Player {
	public PlayerTest(double x, double y, double width, double height) {
		super(x, y, width, height);
		velocity.set(0, 0);
	}

	@Override
	public void update(float dt) {
		System.out.println(dt);
		double acc = 2;
		velocity.translateX(2 * dt);
		bounds.translateX(velocity.getX() * dt);
	}

	@Override
	protected void draw(Graphics2D graphics) {
		graphics.setColor(Color.GREEN);
		graphics.draw(bounds.stroke());
	}

	@Override
	public boolean isRemovable() {
		return false;
	}
	
}
