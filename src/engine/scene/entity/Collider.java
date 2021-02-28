package engine.scene.entity;

import engine.physics2d.Vector;

/**
 * The class {@code Collider} represents a collidable body which is placed under the law of the physics engine.
 */
public abstract class Collider extends Component {
    /**
     * The attribut {@code velocity} specified the linear velocity of this body per update.
     */
    public Vector velocity = new Vector(0, 0); //TODO: protected
    
    /**
    * Impulse is a term that quantifies the overall effect of a force acting on this {@Collider} over time.
    */
    private Vector impulse = new Vector(0, 0);

    /**
     * This coefficient between 0 and 1 sets the elasticity of this body. The value should be in between 0 and 1.
     * The higher is {@code restitution}, the more this {@code Collider} is bouncy.
     */
    public float restitution; //TODO: protected  + default value

    /**
     * This coefficient between 0 and 1 sets the resistance to movement that occurs when colliding this surface.
     * The higher is {@code friction}, the greater will be the deceleration.
     */
    public float friction; //TODO: protected + default value

    /**
     * This flag tells if this body is solid or not.
     */
    protected boolean solid = true;

    /**
     * Tells if this plateform is passable from below.
     */
    protected boolean traversable = false;

    /**
    * Specifies the density of this {@code Collider}. (must be positiv)
    * An object with 0 as invert mass have infinite mass.
    */
    public float im = 0;

    /**
     * Static bodies collide but are immovable.
     * Kinematic bodies are movable but are not be driven by the physics engine.
     * Dynamic bodies move at the whims of physics according to their velocities and other forces, and collision impacts exerted on them.
     */
    public enum CollisionType {
        STATIC, KINEMATIC, DYNAMIC
    }

    protected CollisionType type = CollisionType.STATIC;

    public boolean isDynamic() {
        return type.equals(CollisionType.DYNAMIC);
    }

    public boolean isStatic() {
        return type.equals(CollisionType.STATIC);
    }

	public Collider(float x, float y, float width, float height, int zindex) {
        super(x, y, width, height, zindex);
    }

    //TODO
    // public void init(boolean solid, double im, double friction, double restituion, CollisionType type) {
        
    // }

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

    public void transform(Vector vector) {
        bounds.translate(vector);
    }
    
    public void applyForce(Vector vector) {
        if (type.equals(CollisionType.DYNAMIC))
            velocity.translate(vector);
    }

    public void applyImpulse() {
        velocity.translate(impulse);
        impulse.set(0, 0);
    }
    
    public void corrImpulse() {
        bounds.translate(impulse);
        impulse.set(0, 0);
    }

    public void updateImpulse(Vector forces) {
        if (Math.abs(impulse.getX()) < Math.abs(forces.getX()))
			impulse.setX(forces.getX());
		if (Math.abs(impulse.getY()) < Math.abs(forces.getY()))
			impulse.setY(forces.getY());
    }

    public final class Manifold {
        public Collider colliderA;
        public Collider colliderB;
        public boolean collides;
        public Vector normal;
        public float penetration;
    }

    public Manifold collides(Collider collider) {
        Manifold collision = new Manifold();
        if (collision.collides = bounds.intersects(collider.bounds)) {
            Vector center = bounds.center();
            Vector center2 = collider.bounds.center();
    
            float x = (bounds.getWidth() + collider.bounds.getWidth()) / 2;
            float y = (bounds.getHeight() + collider.bounds.getHeight()) / 2;
            System.out.println(velocity.getY());
            if (collider.traversable)
                collision.normal = (collision.collides = velocity.getY() > 0 && (y - Math.abs(center2.getY() - center.getY())) < 40 * velocity.getY()) ? new Vector(0, 1) : new Vector(0, 0);
            else if (Vector.sub(center, center2).magnitude() > Math.sqrt(x * x  + y * y) - 0.1)                    // penetration < mouvement max quon peut faire en dt x2
                collision.normal = new Vector(0, 0);
            else if (Math.abs(center.getX() - center2.getX()) < collider.bounds.getWidth() / 2)
                collision.normal = new Vector(0, 1);
            else if (Math.abs(center.getY() - center2.getY()) < collider.bounds.getHeight() / 2)
                collision.normal = new Vector(1, 0);
            else {
                Vector relativeCenter = center.clone();
                relativeCenter.translate(Math.signum(center2.getX() - center.getX()) * collider.bounds.getWidth() / 2,
                    Math.signum(center2.getY() - center.getY()) * collider.bounds.getHeight() / 2);
                if (Math.abs(relativeCenter.getX() - center2.getX()) *
                    bounds.getHeight() - Math.abs(relativeCenter.getY() - center2.getY()) * bounds.getWidth() < 0)
                    collision.normal = new Vector(0, 1);
                else
                    collision.normal = new Vector(1, 0);
            }

            if (collision.collides = (collision.collides && (velocity.dot(collision.normal) - collider.velocity.dot(collision.normal))
                * Vector.sub(bounds.getLocation(), collider.bounds.getLocation()).dot(collision.normal) < 0)) {
                if (collision.normal.getX() == 1)
                    collision.penetration = Math.signum(center.getX() - center2.getX()) * (x - Math.abs(center2.getX() - center.getX()));
                else if (collision.normal.getY() == 1)
                    collision.penetration = Math.signum(center.getY() - center2.getY()) * (y - Math.abs(center2.getY() - center.getY()));
                collision.colliderA = this;
                collision.colliderB = collider;
            }
        }
        return collision;
    }

    //public abstract void applyCollision(Manifold manifold);
}
