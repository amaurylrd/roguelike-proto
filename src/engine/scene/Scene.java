package engine.scene;

import engine.stage.Input;
import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.scene.entity.Collider;
import engine.scene.entity.Player;
import engine.physics2d.Force;
import engine.physics2d.Vector;
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
		public double depth;

		/**
		 * The collection of objects in this {@code Layer}.
		 */
		public Collection<Component> objects;

		/**
		 * This class separates different elements of the {@code Scene}.
		 * Layers are superimposed on another one.
		 * 
		 * @param components the list of components
		 * @param distance the depth of this {@code Layer}
		 */
		public Layer(Collection<Component> components, double distance) {
			objects = components;
			depth = distance;
		}
	}

	public static final Integer TILES_LAYER = Integer.valueOf(-1);
	public static final Integer ENTITIES_LAYER = Integer.valueOf(0);

	private Map<Integer, Layer> gameObjects = new TreeMap<Integer, Layer>();

	protected Player player;
	//TODO: list d'ia player
	
	protected Camera camera;

	public Scene() {
		gameObjects.put(TILES_LAYER, new Layer(new ArrayList<>(), 0.0));
		gameObjects.put(ENTITIES_LAYER, new Layer(new ArrayList<>(), 0.0));
	}

	public void start() {
		camera = new Camera(this);
	}

	public void setLayer(int zindex, double depth) {
		gameObjects.computeIfAbsent(zindex, k -> new Layer(new ArrayList<>(), depth));
	}

	public void add(Component... components) {
		for (Component component : components) {
			if (component != null) {
				if (component instanceof Player && player == null) //TODO addPlayer ?
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
	public void update(double dt) {
		//abstract handleInputs()

		if (player != null) {
			int left = Input.isPressed(Input.LEFT) ? -1 : 0;
			int right = Input.isPressed(Input.RIGHT) ? 1 : 0;
			
			double targetvelocity = left * 0.2 + right * 0.2 + player.groundedVelocityX;
			player.velocity.translateX((targetvelocity - player.velocity.getX()) * 0.1);

			
			player.grounded = false;
			for (Component tiles : gameObjects.get(TILES_LAYER).objects) {
				if (tiles.getBounds().intersects(player.feet)) {
					player.grounded = true;
					player.groundedVelocityX = ((Collider) tiles).velocity.getX();
				}
			}

			if (player.grounded || true) {
				//recharge
				if (Input.isPressed(Input.JUMP)) {
					//camera.addTrauma(0.8);
					player.velocity.setY(-0.2);
				}
			}
			
		}

		Collection<Component> prebodies = new ArrayList<Component>(gameObjects.get(ENTITIES_LAYER).objects);
		prebodies.addAll(gameObjects.get(TILES_LAYER).objects);
		List<Collider> bodies = (List<Collider>) (Object) prebodies;

		Collisions.detection(bodies);

		Vector constantForces = Vector.scale(Force.GRAVITY, dt * 0.5);
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
		Collisions.clear();
	}

	//flickering setColor setFont
	@Override
	public void render(Graphics2D graphics) {
		final double nthread = Runtime.getRuntime().availableProcessors();
		final AffineTransform transform = graphics.getTransform();
		graphics.rotate(camera.getRotation(), camera.getWidth() / 2, camera.getHeight() / 2);
		for (Layer layer : gameObjects.values()) {
			List<List<Component>> batches = Lists.chunk(new ArrayList<Component>(layer.objects), (int) Math.ceil((double) layer.objects.size() / nthread));
			Thread threads[] = new Thread[batches.size()];
			for (int i = 0; i < threads.length; ++i) {
				List<Component> batch = batches.get(i);
				threads[i] = new Thread() {
					@Override
					public void run() {
						for (Component component : batch) {
							if (component.isOpaque()) {
								double paralax = layer.depth;
								component.getBounds().translate((1 + 0.05 * paralax) * -camera.getX(), (1 + 0.05 * paralax) * -camera.getY());
								if (camera.focuses(component))
									component.render(graphics);
								component.getBounds().translate((1 + 0.05 * paralax) * camera.getX(), (1 + 0.05 * paralax) * camera.getY());
							}
						}
					}
				};
				threads[i].start();
			}

			for (Thread thread : threads) {
				try {
					thread.join();
				} catch (InterruptedException exception) {}
			}
		}
		graphics.setTransform(transform);
		camera.render(graphics);
	}

	/**
	 * Returns the {@code Camera} linked to this {@code Scene}.
	 * 
	 * @return the camera
	 */
	public Camera getCamera() {
		return camera;
	}
}