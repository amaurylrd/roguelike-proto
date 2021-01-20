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
}