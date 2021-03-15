package com.engine.physics2d;

/**
 * This interface {@code Collidable} defines the methods for a collidable object.
 *
 * @version 1.0 15 Mar 2021
 * @author Amaury Le Roux Dupeyron
 */
public interface Collidable {
    /**
     * This methods stores the collision information and returns it.
     * 
     * @param collider the colliding object
     * @return the {@code Manifold} for this contact
     */
    public abstract Manifold collides(Collider collider);
}
