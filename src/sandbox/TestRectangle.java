package sandbox;

import java.awt.Graphics2D;

import engine.scene.entity.Entity;

public class TestRectangle extends Entity {
    public TestRectangle(int z) {
        super(100, 300, 500, 500, z);
	}

    @Override
    public void update(float dt) {
        double x = bounds.getX() + 20 * dt;
        bounds.setX(x);
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.draw(bounds.stroke());
    }

    @Override
    public boolean isRemovable() {
        return false;
    }
}
