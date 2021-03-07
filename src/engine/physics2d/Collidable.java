package engine.physics2d;

/**
 * This interface {@code Collidable} defines the methods for a collidable object.
 */
public interface Collidable {
    //TODO: commentaire
    public abstract Manifold collides(Collider collider);
}
