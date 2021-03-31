package com.engine.entity;

import com.engine.physics2d.Vector;
import com.engine.physics2d.Collider;

public abstract class Entity extends Collider {
    public Entity(float x, float y, float width, float height, int zindex) {
        super(x, y, width, height, zindex);
        type = CollisionType.DYNAMIC;
        im = 0.05f;
    }

    @Override
	public void update(float dt) {
        transform(Vector.scale(velocity, dt));
    }
}