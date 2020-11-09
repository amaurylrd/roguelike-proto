package engine.scene.entity;

import engine.geom.shape.Rectangle;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Component implements Drawable {
    //Z-index
    private int layer;

    private boolean opaque = true;

    protected Rectangle bounds;

    public Component(double x, double y, double width, double height, int layer) {
        bounds = new Rectangle(x, y, width, height);
        this.layer = layer;
    }

    @Override
    public final void render(Graphics2D graphics) {
        if (!isRemovable()) {
            Color penColor = graphics.getColor();
            draw(graphics);
            graphics.setColor(penColor);
        }
    }

    public abstract void draw(Graphics2D graphics);

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
