package com.engine.physics2d;

/**
 * The class {@code Manifold} is a strucutre that holds collision information.
 *
 * @version 1.0 15 Mar 2021
 * @author Amaury Le Roux Dupeyron
 */
public final class Manifold {
    /**
     * The first object colliding.
     */
    public Collider colliderA;

    /**
     * The second object colliding.
     */
    public Collider colliderB;
    
    /**
     * Tells if the {@code Collider} A and B collides.
     */
    public boolean collides;

    /**
     * The contact normal.
     */
    public Vector normal;

    /**
     * The penetration distance.
     */
    public float penetration;
}