package engine.scene.entity;

import java.awt.Graphics2D;

public interface Drawable {
    public abstract void update(float dt);

    public abstract void render(Graphics2D graphics);
}
