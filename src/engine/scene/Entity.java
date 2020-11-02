package engine.scene;

import engine.geom.shape.Rectangle;

public abstract class Entity implements Drawable {
    protected Rectangle shape;
    private boolean opaque = false;
    private int layer;
    //add animation, hitbox, collision...

    public Entity(int x, int y, double width, double height, int zindex) {
        init(x, y, width, height, zindex);
    }

    public Entity(int x, int y, double width, double height) {
        init(x, y, width, height, 0);
    }

    public void init(int x, int y, double width, double height, int zindex) {
        shape = new Rectangle(x, y, width, height);
        setLayer(zindex);
    }

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
     * @return <i>true</i> if this component is completely opaque
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

//     public int getX() {

//     }
}
