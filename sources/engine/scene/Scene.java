package engine.scene;

import engine.stage.Input;
import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.scene.entity.Player;
import engine.physics2d.Force;
import engine.physics2d.Vector;
import engine.physics2d.Collider;
import engine.util.collection.Lists;
import engine.physics2d.Collisions;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

@SuppressWarnings("unchecked")
public abstract class Scene extends Canvas implements Drawable {
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
		public Collection<Component> objects;

		/**
		 * This class separates different elements of the {@code Scene}. Layers are
		 * superimposed on another one.
		 * 
		 * @param components the list of components
		 * @param distance   the depth of this {@code Layer}
		 */
		public Layer(Collection<Component> components, float distance) {
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

	protected Player player;
	// TODO: list d'ia player

	/**
	 * The 2d {@code Camera} attached to this {@code Scene}.
	 */
	protected Camera camera;

	public Scene() {
		gameObjects.put(TILES_LAYER, new Layer(new ArrayList<>(), 0));
		gameObjects.put(ENTITIES_LAYER, new Layer(new ArrayList<>(), 0));
	}

	public void start() {
		camera = new Camera(this);
	}

	public void setLayer(int zindex, float depth) {
		gameObjects.computeIfAbsent(zindex, k -> new Layer(new ArrayList<>(), depth));
	}

	public void add(Component... components) {
		for (Component component : components) {
			if (component != null) {
				if (component instanceof Player && player == null) // TODO addPlayer ?
					player = (Player) component;
				Layer layer = gameObjects.computeIfAbsent(component.getLayer(),
						k -> new Layer(new ArrayList<Component>(), k));
				layer.objects.add(component);
			}
		}
	}

	public void remove(Component... components) {
		for (Component component : components) {
			Integer key = Integer.valueOf(component.getLayer());
			if (gameObjects.containsKey(key)) {
				Layer layer = gameObjects.get(key);
				Iterator<Component> iterator = layer.objects.iterator();
				while (iterator.hasNext() && !component.equals(iterator.next())) {
					iterator.remove();
					if (layer.objects.isEmpty())
						gameObjects.remove(key);
				}
			}
		}
	}

	@Override
	public void update(float dt) {
		// abstract handleInputs()

		if (player != null) {
			int left = Input.isPressed(Input.LEFT) ? -1 : 0;
			int right = Input.isPressed(Input.RIGHT) ? 1 : 0;

			float targetvelocity = left * 0.2f + right * 0.2f + player.groundVelocity.getX();
			player.velocity.translateX((targetvelocity - player.velocity.getX()) * 0.1f);

			player.grounded = false;
			for (Component tiles : gameObjects.get(TILES_LAYER).objects) {
				if (tiles.getBounds().intersects(player.feet)) {
					player.grounded = true;
					player.groundVelocity.set(((Collider) tiles).velocity);
					//player.groundVelocityX = ((Collider) tiles).velocity.getX();
					//player.groundVelocityY = ((Collider) tiles).velocity.getY();
				}
			}

			if (player.grounded || true) {
				// recharge
				if (Input.isPressed(Input.JUMP)) {
					// camera.addTrauma(0.8);
					player.velocity.setY(player.groundVelocity.getY() - 0.3f);
				}
			}
		}

		Collection<Component> prebodies = new ArrayList<Component>(gameObjects.get(ENTITIES_LAYER).objects);
		prebodies.addAll(gameObjects.get(TILES_LAYER).objects);
		List<Collider> bodies = (List<Collider>) (Object) prebodies;

		Collisions.detection(bodies);

		Vector constantForces = Vector.scale(Force.GRAVITY, dt * 0.5f);
		bodies.forEach((body) -> body.applyForce(constantForces));

		Collisions.resolution();
		bodies.forEach((body) -> body.applyImpulse());

		camera.update(dt);
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

		bodies.forEach((body) -> body.applyForce(constantForces));

		Collisions.correction();
		bodies.forEach((body) -> body.transalteImpulse());
		Collisions.clear();
	}

	// flickering setColor setFont
	@Override
	public void render(Graphics2D graphics) {
		final int nthread = Runtime.getRuntime().availableProcessors();
		final AffineTransform transform = graphics.getTransform();
		graphics.rotate(camera.getRotation(), camera.getWidth() / 2, camera.getHeight() / 2);
		for (Layer layer : gameObjects.values()) {
			List<List<Component>> batches = Lists.chunk(new ArrayList<Component>(layer.objects),
					(int) Math.ceil((double) layer.objects.size() / nthread));
			Thread threads[] = new Thread[batches.size()];
			for (int i = 0; i < threads.length; ++i) {
				List<Component> batch = batches.get(i);
				threads[i] = new Thread() {
					@Override
					public void run() {
						for (Component component : batch) {
							if (component.isOpaque()) {
								float paralax = layer.depth;
								component.getBounds().translate((1 + 0.05f * paralax) * -camera.getX(), (1 + 0.05f * paralax) * -camera.getY());
								if (camera.focuses(component)) //remplacer le draw ici
									component.render(graphics);
								component.getBounds().translate((1 + 0.05f * paralax) * camera.getX(), (1 + 0.05f * paralax) * camera.getY());
							}
						}
					}
				};
				threads[i].start();
			}

			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException exception) {
				}
			}
		}
		graphics.setTransform(transform);
		camera.render(graphics);
	}

	public void CL_render() {
		for (Layer layer : gameObjects.values()) {
			for (Component component : layer.objects) {
				
				engine.render2d.OpenCL.XXX(getBuffer(), component.texture, component.getBounds().getX(), component.getBounds().getY());
			}
		}
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