package engine.scene.entity.particle;

import java.awt.Graphics2D;
import java.awt.Color;

import engine.scene.entity.Entity;
import engine.util.Random;

public abstract class Particle extends Entity {
    private int rgb = 0xffffffff;
    private int alpha = 255;

    public Particle(double x, double y, double size, int layer) {
        super(x, y, size, size, layer);
        velocity.set(Random.nextDouble(-1, 1), Random.nextDouble(-1, 1));
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
        bounds.setX(bounds.getX() + velocity.getX()*dt);
        bounds.setY(bounds.getY() + velocity.getY()*dt);
        alpha -= 1; //fade
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(getColor());
        double size = bounds.getSize().width;
        graphics.fill(new java.awt.geom.Ellipse2D.Double(bounds.getX(), bounds.getY(), size, size));
    }

    @Override
    public boolean isRemovable() {
        return alpha < 0;
    }
}
