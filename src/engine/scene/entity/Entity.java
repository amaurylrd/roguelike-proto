package engine.scene.entity;

import engine.physics2d.Vector;

public abstract class Entity extends Collider {
    /**
     * Impulse is a term that quantifies the overall effect of a force acting on this {@Entity} over time.
     */
    private Vector impulse = new Vector(0, 0);

    /**
     * Specifies the density of this {@code Entity}.
     */
    public double mass;

    //private boolean grounded = false;
    //private Map<String, Sprite> sprites;
    //private String currentSprite;

    public Entity(double x, double y, double width, double height, int zindex) {
        super(x, y, width, height, zindex);
    }

    @Override
	public void update(double dt) {
        bounds.translate(Vector.scale(velocity, dt));
    }

    //sprites = new HashMap<String, Sprite>();
    //currentSprite = null;
    
    //@Override
    public void applyCollision(Manifold manifold) {
        final Vector normal = manifold.normal;
        Entity collider = (Entity) manifold.collider;
        double v1 = velocity.dot(normal), v2 = collider.velocity.dot(normal);
        if ((v1 - v2) * Vector.sub(bounds.getLocation(), collider.bounds.getLocation()).dot(normal) < 0) {
		    double vf = (restitution + collider.restitution) * (2 * collider.mass * v2 +  (mass - collider.mass) * v1) / (mass + collider.mass);
		    double vf2 = (restitution + collider.restitution) * (2 * mass * v1 +  (collider.mass - mass) * v2) / (mass + collider.mass);
		    updateImpulse(Vector.scale(normal, vf - v1));
            collider.updateImpulse(Vector.scale(normal, vf2 - v2));
        }
    }

    public void applyForce(Vector vector) {
        velocity.translate(vector);
    }

    public void applyImpulse() {
        //velocity += impulse / m;
        velocity.translate(impulse);
        impulse.set(0, 0);
    }

    public void updateImpulse(Vector forces) {
        if (Math.abs(impulse.getX()) < Math.abs(forces.getX()))
			impulse.setX(forces.getX());
		if (Math.abs(impulse.getY()) < Math.abs(forces.getY()))
			impulse.setY(forces.getY());
    }
}