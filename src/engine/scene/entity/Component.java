package engine.scene.entity;

import engine.geom.shape.Rectangle;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

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

    //la texture
    public BufferedImage texture = engine.application.Ressources.ressource("spritetest");;

    /**
     * This is the fill color.
     */
    public Color color = Color.WHITE;
    
    //TODO effacer ces merdes inutiles
    private class VertexObjectBuffer {
        public float x;
        public float y;
        public float width;
        public float height;
        public float rotation;
    }

    public VertexObjectBuffer getVBO() {
        VertexObjectBuffer vbo = new VertexObjectBuffer();
        vbo.x = bounds.getX();
        vbo.y = bounds.getY();
        vbo.width = bounds.getWidth();
        vbo.height = bounds.getHeight();
        return vbo;
    }
    /**
     * Produces the vertex object buffer for this {@code Component} <red, green, blue, alpha, x, y, width, height>.
     * 
     * @return the vertex object buffer
     */
    public float[] getVertexObjectBuffer() {
        return color.getComponents(new float[] { 0, 0, 0, 0, bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight() });
    }//TODO: rotation

    /**
     * The render method is called to draw this {@code Component}.
     * 
     * @param graphics the canvas graphics context
     * @see draw(Graphics2D graphics)
     * @see isRemovable()
     */
    @Override
    public void render(Graphics2D graphics) {
        if (!isRemovable() && isOpaque()) {
            // Color penColor = graphics.getColor();
            draw(graphics);
            // graphics.setColor(penColor);
        }
    }

    protected abstract void draw(Graphics2D graphics);

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
     * Draws the bounds of this {@code Component}.
     * 
     * @param graphics the graphics context
     * @param color    the color of the bounds
     */
    protected void drawBounds(Graphics2D graphics, Color color) { // TODO dans une classe Graphics2d
        // Color penColor = graphics.getColor();
        // graphics.setColor(color);
        graphics.draw(bounds.stroke());
        // graphics.setColor(penColor);
    }

    @Override
    public String toString() { //getbuffer Arrays.toString()
        return this.getClass().getSimpleName() + " [x:" + bounds.getX() + ", y:" + bounds.getY() + ", width:" + bounds.getWidth() + ", height:" + bounds.getHeight() + "]";
    }
}
