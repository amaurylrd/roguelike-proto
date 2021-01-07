package sandbox;

import java.awt.Graphics2D;

import engine.scene.entity.Entity;

public class TestRectangle extends Entity {
    public TestRectangle(int z) {
        super(100, 300, 200, 100, z);
	}

    @Override
    public void update(double dt) {
        //float x = bounds.getX() + 20 * dt;
        //bounds.setX(x);
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
