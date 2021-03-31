package com.engine.scene;

import javax.media.opengl.GL2;
import com.engine.geom.Rectangle;

//TODO add method setX, setY, setLocation, setSize, setWidth, setHeight
//TODO comment
public abstract class Component implements Drawable {
    /**
     * The z-index of this {@code Component}. This index can take negativ value.
     */
    protected int layer;

    /**
     * This {@code Component} opacity.
     */
    protected boolean opaque = true;

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
     * @param zindex the z-index of this {@code Component}
     */
    public Component(float x, float y, float width, float height, int zindex) {
        bounds = new Rectangle(x, y, width, height);
        layer = zindex;
    }

    /**
     * The render method is called to draw this {@code Component}.
     * 
     * @param graphics the canvas graphics context
     * @see draw(Graphics2D graphics)
     * @see isRemovable()
     */
    @Override
    public void render(GL2 graphics) {
        if (!isRemovable() && isOpaque())
            draw(graphics);
    }

    //TODO comment
    protected abstract void draw(GL2 graphics);

    /**
     * Checks if this {@code Component} could be remove.
     * 
     * @return <i>true</i> if this {@code Component} has to be remove from draw
     *         calls, <i>false</i> otherwise
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
     * @return <i>true</i> if this {@code Component} is completely opaque,
     *         <i>false</i> otherwise
     * @see setOpaque(boolean isOpaque)
     */
    public boolean isOpaque() {
        return opaque;
    }

    /**
     * Gets the z-index of this {@code Component}.
     * 
     * @return the z-index
     */
    public int getLayer() {
        return layer;
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
     * Returns the width of this {@code Component}.
     * 
     * @return the width
     */
    public float getWidth() {
        return bounds.getWidth();
    }

    /**
     * Returns the height of this {@code Component}.
     * 
     * @return the height
     */
    public float getHeight() {
        return bounds.getHeight();
    }

    /**
     * Returns the x coordinate of this {@code Component}.
     * 
     * @return the x coordinate
     */
    public float getX() {
        return bounds.getX();
    }

    /**
     * Returns the y coordinate of this {@code Component}.
     * 
     * @return the y coordinate
     */
    public float getY() {
        return bounds.getY();
    }

    /**
	 * Returns the representation of this {@code Component} as {@code String}.
	 * 
	 * @return the {@code String} representing this object
	 */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + bounds;
    }
}
