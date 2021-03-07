package engine.scene.entity;

import engine.physics2d.Vector;
import engine.physics2d.Collider;
public abstract class Entity extends Collider {
    //private boolean grounded = false;
    //private Map<String, Sprite> sprites;
    //private String currentSprite;

    public Entity(float x, float y, float width, float height, int zindex) {
        super(x, y, width, height, zindex);
        type = CollisionType.DYNAMIC;
        im = 0.05f;
    }

    @Override
	public void update(float dt) {
        transform(Vector.scale(velocity, dt));
    }

    //sprites = new HashMap<String, Sprite>();
    //currentSprite = null;
    
    //@Override
    //TODO applyCol(Vector normal, Component ta mère)
}