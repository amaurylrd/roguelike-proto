package com.engine.scene;

import com.jogamp.opengl.GL2;

/**
 * This interface {@code Drawable} defines any graphical game object.
 */
public interface Drawable {
    /**
     * Called whenever a game object needs to be updated depending on the UPS.
     * 
     * @param dt the delta time between updates at render speed
     */
    public abstract void update(float dt);

    /**
     * Called to render a game object into the scene depending on the FPS.
     * 
     * @param graphics the canvas graphics context
     */
    public abstract void render(GL2 graphics);
}
