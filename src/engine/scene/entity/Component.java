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
     * The render method is called to draw
     * 
     * @param graphics 
     * @see draw
     * @see isRemovable
     */
    @Override
    public final void render(Graphics2D graphics) {
        if (!isRemovable()) {
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
     * Checks whether or not this component is opaque.
     * 
     * @return <i>true</i> if this component is completely opaque, <i>false</i> otherwise
     * @see setOpaque(boolean isOpaque)
     */
    public boolean isOpaque() {
    	return opaque;
    }

    /**
     * Gets the z-index of this component.
     * 
     * @return the z-index
     */
    public int getLayer() {
        return layer;
    }

    /**
     * Sets the z-index of this component.
     * 
     * @param zindex the stack level of the generated box in the current stacking context
     */
    public void setLayer(int zindex) {
        layer = zindex;
    }
}
