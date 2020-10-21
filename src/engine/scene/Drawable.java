package engine.scene;

public abstract class Drawable {
    protected boolean opaque = false;

    public abstract void update();

    public abstract void render(float dt);

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
}
