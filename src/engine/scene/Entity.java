package engine.scene;

import engine.geom.shape.Rectangle;

public abstract class Entity implements Drawable {
    protected Rectangle offbox;
    private boolean opaque = false;
    private boolean grounded = false;
    private boolean solid = false;
    private int layer;
    //add animation, hitbox

    public Entity(int x, int y, double width, double height, int zindex) {
        init(x, y, width, height, zindex);
    }

    public Entity(int x, int y, double width, double height) {
        init(x, y, width, height, 0);
    }

    public void init(int x, int y, double width, double height, int zindex) {
        offbox = new Rectangle(x, y, width, height);
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
     * @return <i>true</i> if this component is completely opaque, <i>false</i> otherwise
     * @see setOpaque(boolean isOpaque)
     */
    public boolean isOpaque() {
    	return opaque;
    }

    /**
     * Specifies whether this component is grounded or not.
     * 
     * @param isGrounded <i>true</i> if this component should be grounded
     * @see isGrounded()
     */
    public void setGrounded(boolean isGrounded) {
    	grounded = isGrounded;
    }

    /**
     * Checks whether or not this component is grounded.
     * 
     * @return <i>true</i> if this component is grounded, <i>false</i> otherwise
     * @see setGrounded(boolean isGrounded)
     */
    public boolean isGrounded() {
    	return grounded;
    }

    /**
     * Specifies whether this component is solid or not.
     * 
     * @param isSolid <i>true</i> if this component should be solid
     * @see isSolid()
     */
    public void setSolid(boolean isSolid) {
    	solid = isSolid;
    }

    /**
     * Checks whether or not this component is solid.
     * 
     * @return <i>true</i> if this component is solid, <i>false</i> otherwise
     * @see setSolid(boolean isSolid)
     */
    public boolean isSolid() {
    	return solid;
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
