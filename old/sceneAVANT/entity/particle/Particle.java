package com.engine.scene.entity.particle;

import java.awt.Graphics2D;
import java.awt.Color;

import com.engine.scene.entity.Entity;
import com.engine.util.Random;

public abstract class Particle extends Entity {
    private int rgb = 0xffffffff;
    private int alpha = 255;

    public Particle(float x, float y, float size, int zindex) {
        super(x, y, size, size, zindex);
        velocity.set(Random.nextFloat(-1, 1), Random.nextFloat(-1, 1));
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
        bounds.setX(bounds.getX() + velocity.getX() * dt);
        bounds.setY(bounds.getY() + velocity.getY() * dt);
        alpha -= 1; //fade
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(getColor());
        float size = bounds.getSize().width;
        graphics.fill(new java.awt.geom.Ellipse2D.Float(bounds.getX(), bounds.getY(), size, size));
    }

    @Override
    public boolean isRemovable() {
        return alpha < 0;
    }
}
