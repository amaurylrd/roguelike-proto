package engine.scene;

import engine.geom.shape.Rectangle;

public class Entity extends Drawable {
    private Rectangle shape;

    public Entity(int x, int y, double width, double height) {
        shape = new Rectangle(x, y, width, height);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(float dt) {

    }
}
