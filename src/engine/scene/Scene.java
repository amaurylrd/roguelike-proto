package engine.scene;

import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.scene.entity.Entity;
import engine.scene.entity.Collider;
import engine.scene.entity.Player;
import engine.physics2d.Force;
import engine.physics2d.Vector;
import engine.util.collection.Lists;
import engine.physics2d.Collisions;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.Graphics2D;



import sandbox.Input;

public abstract class Scene extends Canvas implements Drawable {
	//TODO static physcalLayer ?
	private Map<Integer, Collection<Component>> gameObjects = new TreeMap<Integer, Collection<Component>>();
	protected Player player;
	private Camera camera;
	
	public void start() {
		camera = new Camera(this);
	}

	public void add(Component... components) {
		for (Component component : components) {
			if (component != null) {
				if (component instanceof Player)
					player = (Player) component;
				Collection<Component> layer = gameObjects.computeIfAbsent(component.getLayer(), k -> new ArrayList<Component>());
				layer.add(component);
			}
		}
	}

	public void remove(Component... components) {
		for (Component component : components) {
			int key = component.getLayer();
			if (gameObjects.containsKey(key)) {
				Collection<Component> layer = Collections.synchronizedCollection(new ArrayList<Component>(gameObjects.get(key)));
				synchronized (layer) {
					Iterator<Component> iterator = layer.iterator();
					while (iterator.hasNext() && !component.equals(iterator.next())) {
						iterator.remove();
						if (layer.isEmpty())
							gameObjects.remove(key);
					}
				}
			}
		}
	}

	@Override
	public void update(double dt) {
		//abstract handleInputs()
		if (player != null) {
			int left = Input.isPressed(Input.LEFT) ? 1 : 0;
			int right = Input.isPressed(Input.RIGHT) ? 1 : 0;
			double targetvelocity = left * -0.5 + right * 0.5;
			
			player.velocity.translateX((targetvelocity - player.velocity.getX()) * 0.1);
			
			if (Input.isPressed(Input.JUMP)) {
				//camera.shake(300);
				player.velocity.setY(-0.5);
			}
		}

		List<Entity> entities = new ArrayList<Entity>();
		Collection<Collider> tiles = new ArrayList<Collider>();
		for (Component component : gameObjects.get(Integer.valueOf(player.getLayer()))) {
			if (component instanceof Entity) {
				Entity entity = (Entity) component;
				entity.velocity.translateY(Force.GRAVITY * dt);
				entities.add(entity);
			} else
				tiles.add((Collider) component);
		}
			
		Collisions.detection(entities, tiles);
		Collisions.resolve();
			
		entities.forEach((entity) -> entity.applyImpulse());

		camera.update(dt);
		for (Collection<Component> layer : gameObjects.values()) {
			Iterator<Component> iterator = layer.iterator();
			while (iterator.hasNext()) {
				Component component = iterator.next();
				if (component.isRemovable())
					iterator.remove();
				else
					component.update(dt);
			}
		}
		
		Collisions.correction();
		Collisions.clear();
	}

	//flickering setColor setFont
	@Override
	public void render(Graphics2D graphics) {
		final int nthread = Runtime.getRuntime().availableProcessors();
		for (Collection<Component> layer : gameObjects.values()) {
			List<List<Component>> batches = Lists.chunk(new ArrayList<Component>(layer), (int) Math.ceil(layer.size() / nthread));
			Thread threads[] = new Thread[batches.size()];
			for (int i = 0; i < batches.size(); ++i) {
				List<Component> batch = batches.get(i);
				threads[i] = new Thread() {
					@Override
					public void run() {
						for (Component component : batch) {
							if (component.isOpaque()) {
								int zindex = component.getLayer(); //TODO depth by layer
								component.getBounds().translate((1 + 0.05 * zindex) * -camera.getX(), (1 + 0.05 * zindex) * -camera.getY());
								if (camera.focuses(component))
									component.render(graphics);
								component.getBounds().translate((1 + 0.05 * zindex) * camera.getX(), (1 + 0.05 * zindex) * camera.getY());
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
	}
}