package engine.scene.entity.particle;

import engine.scene.entity.Entity;
import engine.util.Random;

import java.awt.Graphics2D;
import java.awt.Color;

public class Particle extends Entity {
    private int rgb = 0xffffffff;
    private int alpha = 255;

    public Particle(int x, int y, double size, int layer) {
        super(x, y, size, size, layer);
        veclocity.setSpeed(Random.random(-1, 1), Random.random(-1, 1));
    }

    public void setColor(int r, int g, int b) {
        rgb = new Color(r, g, b).getRGB();
    }

    public Color getColor() {
        int rgba = (alpha << 24) | (rgb & 0x00ffffff);
        return new Color(rgba, true);
    }

    @Override
    public void update(float dt) {
        bounds.position.setX(bounds.position.getX() + veclocity.getX()*dt);
        bounds.position.setY(bounds.position.getY() + veclocity.getY()*dt);
        alpha -= 1;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(getColor());
        double size = bounds.dimension.getWidth();
        graphics.fill(new java.awt.geom.Ellipse2D.Double(bounds.position.getX(), bounds.position.getY(), size, size));
    }

    @Override
    public boolean isRemovable() {
        return alpha < 0;
    }
}
