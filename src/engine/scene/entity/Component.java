package engine.scene.entity;

import engine.geom.shape.Rectangle;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Component implements Drawable {
    /**
     * The z-index of this {@code Component}.
     * This index can take negativ value.
     */
    private int layer;

    /**
     * This {@code Component} opacity.
     */
    private boolean opaque = true;

    /**
     * The bounding box of this {Component}.
     */
    protected Rectangle bounds;

    /**
     * Constructs a {@code Component} with a specified size, location and layer.
     * 
     * @param x the x coordinate of the bounding {@code Rectangle}
     * @param y the y coordinate of the bounding {@code Rectangle}
     * @param width the width of the bounding {@code Rectangle}
     * @param height the height of the bounding {@code Rectangle}
     * @param layer the z-index of this {@code Component}
     */
    public Component(double x, double y, double width, double height, int layer) {
        bounds = new Rectangle(x, y, width, height);
        this.layer = layer;
    }

    /**
     * The render method is called to draw this {@code Component}.
     * 
     * @param graphics 
     * @see draw
     * @see isRemovable
     */
    @Override
    public final void render(Graphics2D graphics) {
        if (!isRemovable() && isOpaque()) {
            Color penColor = graphics.getColor();
            draw(graphics);
            graphics.setColor(penColor);
        }
    }

    protected abstract void draw(Graphics2D graphics);

    /**
     * Checks if this {@code Component} could be remove.
     * 
     * @return <i>true</i> if this {@code Component} has to be remove from draw calls, <i>false</i> otherwise
     */
    public abstract boolean isRemovable();

    /**
     * Sets opacity as specified.
     * 
     * @param isOpaque <i>true</i> if this component should be opaque
     * @see isOpaque()
     */
    public void setOpaque(boolean isOpaque) {
    	opaque = isOpaque;
    }

    /**
     * Checks whether or not this {@code Component} is opaque.
     * 
     * @return <i>true</i> if this {@code Component} is completely opaque, <i>false</i> otherwise
     * @see setOpaque(boolean isOpaque)
     */
    public boolean isOpaque() {
    	return opaque;
    }

    /**
     * Gets the z-index of this {@code Component}.
     * 
     * @return the z-index
     * @see setLayer(int zindex)
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Sets the z-index of this {@code Component}.
     * 
     * @param zindex the stack level of the generated box in the current stacking context
     * @see getLayer()
     */
    public void setLayer(int zindex) {
        layer = zindex;
    }

    /**
     * Returns the bouding box of this {@code Component}.
     * 
     * @return the bounds
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Draws the bounds of this {@code Component}.
     * 
     * @param graphics the graphics context
     * @param color the color of the bounds
     */
    protected void drawBounds(Graphics2D graphics, Color color) {
        Color penColor = graphics.getColor();
        graphics.setColor(color);
        graphics.draw(bounds.stroke());
        graphics.setColor(penColor);
    }
}
