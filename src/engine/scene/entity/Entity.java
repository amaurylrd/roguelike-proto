package engine.scene.entity;

import engine.physics2d.Vector;

public abstract class Entity extends Collider {
    //private boolean grounded = false;
    //private Map<String, Sprite> sprites;
    //private String currentSprite;

    public Entity(double x, double y, double width, double height, int zindex) {
        super(x, y, width, height, zindex);
        type = CollisionType.DYNAMIC;
        im = 0.05;
    }

    @Override
	public void update(double dt) {
        transform(Vector.scale(velocity, dt));
    }

    //sprites = new HashMap<String, Sprite>();
    //currentSprite = null;
    
    //@Override
    //TODO applyCol(Vector normal, Component ta mère)
    public void applyCollision(Manifold manifold) {
        final Vector normal = manifold.normal;
        Entity collider = (Entity) manifold.colliderB;

        double v1 = velocity.dot(normal), v2 = collider.velocity.dot(normal);
        double maxRestitution = Math.max(restitution, collider.restitution);

        double vf = maxRestitution * (2 * collider.mass * v2 + (mass - collider.mass) * v1) / (mass + collider.mass);
        updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01 ? 0 : vf) - v1));

        vf = maxRestitution * (2 * mass * v1 + (collider.mass - mass) * v2) / (mass + collider.mass);
        collider.updateImpulse(Vector.scale(normal, (Math.abs(vf) < 0.01 ? 0 : vf) - v2));
    }
}