package engine.scene.entity;

import engine.physics2d.Vector;

/**
 * The class {@code Collider} a collidable body which are placed under the law of the physics engine.
 */
public abstract class Collider extends Component {
    /**
     * The attribut {@code velocity} specified the linear velocity of this body per update.
     */
    public Vector velocity = new Vector(0, 0); //TODO: protected
    
    /**
     * This coefficient sets the elasticity of this body. The value should be in between 0 and 1.
     * The higher is {@code restitution}, the more this {@code Collider} is bouncy.
     */
    public double restitution; //TODO: protected

    /**
     * This coefficient sets the resistance to movement that occurs when colliding this surface.
     * The higher is {@code friction}, the greater will be the deceleration.
     */
    public double friction; //TODO: protected

    /**
     * This flag tells if this body is solid or not.
     * If set to <i>true</i> objetcs will go through this {@collider Collider}.
     */
    protected boolean solid = true;

    /**
     * Tells if this plateform is passable from below.
     */
    protected boolean traversable = false;

    /**
     * Kinematic bodies are movable but are not be driven by the physics engine.
     */
    protected boolean kinematic = false;

    /**
     * Tells whether this {@code Collider} is kinematic.
     * 
     * @return <i>true</i> if this {@code Collider} is kinematic, <i>false</i> otherwise
     */
    public boolean isKinematic() {
        return kinematic;
    }

	public Collider(double x, double y, double width, double height, int zindex) {
        super(x, y, width, height, zindex);
	}

	/**
     * Specifies whether this {@code Collider} is solid or not.
     * 
     * @param isSolid <i>true</i> if this {@code Collider} should be solid
     * @see isSolid()
     */
    public void setSolid(boolean isSolid) {
        solid = isSolid;
    }

    /**
     * Checks whether or not this {@code Collider} is solid.
     * 
     * @return <i>true</i> if this {@code Collider} is solid, <i>false</i> otherwise
     * @see setSolid(boolean isSolid)
     */
    public boolean isSolid() {
        return solid;
    }

    public final class Manifold {
        public Collider collider;
        public boolean collides;
        public Vector normal;
        public double penetration;
    }

    public Manifold collides(Collider collider) {
        Manifold collision = new Manifold();
        if (collision.collides = bounds.intersects(collider.bounds)) {
            collision.collider = collider;

            Vector center = bounds.center();
            Vector center2 = collider.bounds.center();
    
            double x = (bounds.getWidth() + collider.bounds.getWidth()) / 2;
            double y = (bounds.getHeight() + collider.bounds.getHeight()) / 2;
    
            if (collider instanceof Tile && collider.traversable)
                collision.normal = velocity.getY() < 0 ? new Vector(0, 0) : new Vector(0, 1);
            else if (Vector.sub(center, center2).magnitude() > Math.sqrt(x * x  + y * y) - 0.1)
                collision.normal = new Vector(0, 0);
            else if (Math.abs(center.getX() - center2.getX()) < collider.bounds.getWidth() / 2)
                collision.normal = new Vector(0, 1);
            else if (Math.abs(center.getY() - center2.getY()) < collider.bounds.getHeight() / 2)
                collision.normal = new Vector(1, 0);
            else {
                Vector relativeCenter = center.clone();
                relativeCenter.translate(Math.signum(center2.getX() - center.getX()) * collider.bounds.getWidth() / 2,
                    Math.signum(center2.getY() - center.getY()) * collider.bounds.getHeight() / 2);
                if (Math.abs(relativeCenter.getX() - center2.getX()) * bounds.getHeight() - Math.abs(relativeCenter.getY() - center2.getY()) * bounds.getWidth() < 0)
                    collision.normal = new Vector(0, 1);
                else
                    collision.normal = new Vector(1, 0);
            }

            if (collision.normal.getX() == 1)
                collision.penetration = Math.signum(center.getX() - center2.getX()) * (2 + x - Math.abs(center2.getX() - center.getX()));
            else if (collision.normal.getY() == 1)
                collision.penetration = Math.signum(center.getY() - center2.getY()) * (2 + y - Math.abs(center2.getY() - center.getY()));
        }
        return collision;
    }

    //public abstract void applyCollision(Manifold manifold);
}
