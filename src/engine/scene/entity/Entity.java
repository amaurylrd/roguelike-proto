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
}