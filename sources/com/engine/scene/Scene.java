package com.engine.scene;

import com.engine.entity.Player;
import com.engine.graphics2d.EventListener;
import com.engine.physics2d.Collider;
import com.engine.physics2d.Collisions;
import com.engine.physics2d.Force;
import com.engine.physics2d.Vector;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import com.jogamp.opengl.GL2;

@SuppressWarnings (value="unchecked")
public abstract class Scene implements EventListener, Drawable {
    /**
	 * The class {@code Layer} stores a depth for a collection of {@code Component}.
	 */
	private class Layer {
		/**
		 * This attribut represents the distance from the foreground.
		 */
		public float depth;

		/**
		 * The collection of objects in this {@code Layer}.
		 */
		public List<Component> objects;

		/**
		 * This class separates different elements of the {@code Scene}. Layers are
		 * superimposed on another one.
		 * 
		 * @param components the list of components
		 * @param distance   the depth of this {@code Layer}
		 */
		public Layer(List<Component> components, float distance) {
			objects = components;
			depth = distance;
		}
	}

    /**
	 * This layer is reserved to tiles.
	 */
	public static final Integer TILES_LAYER = Integer.valueOf(-1);

	/**
	 * This layer contains the rest of collidable objects.
	 */
	public static final Integer ENTITIES_LAYER = Integer.valueOf(0);

    /**
	 * This maps a z-index to a collection of objects.
	 */
	private Map<Integer, Layer> gameObjects = new TreeMap<Integer, Layer>();

	/**
	 * The player attached to this {@code Scene}.
	 */
	private Player player;

	/**
	 * The 2D {@code Camera} attached to this {@code Scene}.
	 */
	protected Camera camera;

    public Scene() {
        gameObjects.put(TILES_LAYER, new Layer(new ArrayList<>(), 0));
		gameObjects.put(ENTITIES_LAYER, new Layer(new ArrayList<>(), 0));
    }

    public void add(Component... components) {
		for (Component component : components) {
			if (component != null) {
				if (component instanceof Player && player == null)
					player = (Player) component;
				Layer layer = gameObjects.computeIfAbsent(component.getLayer(), k -> new Layer(new ArrayList<Component>(), k));
				layer.objects.add(component);
			}
		}
	}

    public void remove(Component... components) {
        for (Component component : components) {
            Integer key = Integer.valueOf(component.getLayer());
            if (gameObjects.containsKey(key)) {
                Layer layer = gameObjects.get(key);
                if (layer.objects.remove(component) && layer.objects.isEmpty())
					gameObjects.remove(key);
            }
        }
	}

    @Override
    public void end() {
        gameObjects.clear();
    }

    @Override
    public void begin(GL2 graphics) {
        render(graphics);
    }

	com.sandbox.ShaderProgram shader = null;

    @Override
    public void render(GL2 graphics) {
		if (shader == null)
			shader = new com.sandbox.StaticShader(graphics);
		shader.start(graphics);
        for (Layer layer : gameObjects.values()) {
            for (Component component : layer.objects) {
                if (component.isOpaque()) {
                    component.render(graphics);
                }
            }
        }
		shader.stop(graphics);
		//camera.render()
    }

    @Override
    public void update(float dt) {
        // if (player != null) {
		// 	int left = Input.isPressed(Input.LEFT) ? -1 : 0;
		// 	int right = Input.isPressed(Input.RIGHT) ? 1 : 0;

		// 	float targetvelocity = left * 0.2f + right * 0.2f + player.groundVelocity.getX();
		// 	player.velocity.translateX((targetvelocity - player.velocity.getX()) * 0.1f);

		// 	player.grounded = false;
		// 	for (Component tiles : gameObjects.get(TILES_LAYER).objects) {
		// 		/*if (tiles.getBounds().intersects(player.feet)) {
		// 			player.grounded = true;
		// 			player.groundVelocity.set(((Collider) tiles).velocity);
		// 			//player.groundVelocityX = ((Collider) tiles).velocity.getX();
		// 			//player.groundVelocityY = ((Collider) tiles).velocity.getY();
		// 		}*/
		// 	}

		// 	if (player.grounded || true) {
		// 		// recharge
		// 		if (Input.isPressed(Input.JUMP)) {
		// 			// camera.addTrauma(0.8);
		// 			player.velocity.setY(player.groundVelocity.getY() - 0.3f);
		// 		}
		// 	}
		// }

		List<Component> prebodies = new ArrayList<Component>(gameObjects.get(ENTITIES_LAYER).objects);
		prebodies.addAll(gameObjects.get(TILES_LAYER).objects);
		List<Collider> bodies = (List<Collider>) (Object) prebodies;

		Collisions.detection(bodies);

		Vector constantForces = Vector.scale(Force.GRAVITY, dt * 0.5f);
		bodies.forEach((body) -> body.applyForce(constantForces));

		Collisions.resolution();
		bodies.forEach((body) -> body.applyImpulse());
		
		//camera.update(dt);
		for (Layer layer : gameObjects.values()) {
			Iterator<Component> iterator = layer.objects.iterator();
			while (iterator.hasNext()) {
				Component component = iterator.next();
				if (component.isRemovable())
					iterator.remove();
				else
					component.update(dt);
			}
		}
		bodies.forEach((body) -> body.applyForce(constantForces)); //que les entites

		Collisions.correction();
		bodies.forEach((body) -> body.translateImpulse());
		Collisions.clear();
    }

	/**
	 * Returns the {@code Camera} linked to this {@code Scene}.
	 * 
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * Gets the {@code Player} instance in this {@code Scene}.
	 * 
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
}
