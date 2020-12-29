package sandbox.collission;

import java.awt.Graphics2D;
import java.awt.Color;
import engine.scene.entity.Player;

public class PlayerTest extends Player {
	public PlayerTest(double x, double y, double width, double height) {
		super(x, y, width, height);
	}

	@Override
	public void update(float dt) {
		bounds.translateX(2);
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
