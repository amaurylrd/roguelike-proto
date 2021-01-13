package engine.scene;

import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.scene.entity.Entity;
import engine.scene.entity.Tile;
import engine.scene.entity.Entity.Collision;
import engine.stage.Stage;
import engine.scene.entity.Player;
import engine.physics2d.Force;
import engine.physics2d.Vector;
import engine.util.collection.Lists;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.Graphics2D;



import sandbox.Input;

public class Scene extends Canvas implements Drawable {
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
		dt /= 10;
		//if (player != null) {

			int x = Input.isPressed(Input.LEFT) ? 1 : 0;
			int x2 = Input.isPressed(Input.RIGHT) ? 1 : 0;
			double targetvelocity = x * -0.5 + x2 * 2;
			
			player.velocity.translateX((targetvelocity - player.velocity.getX()) * 0.1);

			if (Input.isPressed(Input.JUMP)) {
				//camera.shake(300);
				player.velocity.setY(-1.5);
			}

			Collection<? extends Component> layer = gameObjects.get(Integer.valueOf(player.getLayer()));
			List<Entity> entities = new ArrayList<Entity>();
			Collection<Tile> tiles = new ArrayList<Tile>();
			
			for (Component component : layer) {
				if (component instanceof Entity) {
					Entity entity = (Entity) component;
					entity.velocity.translateY(Force.GRAVITY * dt);
					entities.add(entity);
				} 
				else if (component instanceof Tile) {
					tiles.add((Tile) component);
				}
			}
			
			Collection<Collision> contacts = new ArrayList<Collision>();
			for (int i = 0; i < entities.size(); ++i) {
				Entity entity = entities.get(i);
				for (int j = i + 1; j < entities.size(); ++j) {
					Collision collision = entity.collides(entities.get(j));
					if (collision.collides)
						contacts.add(collision);
				}

				for (Tile tile : tiles) {
					Collision collision = entity.collides(tile);
					if (collision.collides)
						contacts.add(collision);
				}
			}
			
			//TODO changer list
			for (Collision contact : contacts) {
				Entity entity = contact.A;

				if (contact.B instanceof Entity) {
					final Vector normal = contact.normal;

					Entity colldier = (Entity) contact.B;

					double v1 = entity.velocity.dot(normal);
					double v2 = colldier.velocity.dot(normal);
					double m1 = entity.mass;
					double m2 = colldier.mass;
					double vf = (entity.restitution + colldier.restitution) * (2 * m2 * v2 +  (m1 - m2) * v1) / (m1 + m2);
					double vf2 = (entity.restitution + colldier.restitution) * (2 * m1 * v1 +  (m2 - m1) * v2) / (m1 + m2);
						
					entity.impulse.translate(Vector.scale(normal, vf - v1));
					colldier.impulse.translate(Vector.scale(normal, vf2 - v2));
				} else if (contact.B instanceof Tile) {
					System.out.println(contact);
					Tile colldier = (Tile) contact.B;
					
					final Vector normal = contact.normal;
					final Vector tangeante = new Vector(-normal.getY(), normal.getX());
						
					double bounce = (1 + 0*Math.max(entity.restitution, colldier.restitution)) * entity.velocity.dot(normal);
					double frict = 50/entity.mass * Math.min(entity.friction, colldier.friction) * entity.velocity.dot(tangeante);
					entity.impulse.translate(Vector.scale(normal, -bounce));
					entity.impulse.translate(Vector.scale(tangeante, -frict));
				}
			}

			
			for (Entity entity : entities) {
				entity.applyImpulse();
			}

		

		camera.update(dt);
		for (Collection<Component> layer2 : gameObjects.values()) {			
			Iterator<Component> iterator = layer2.iterator();
			while (iterator.hasNext()) {
				Component component = iterator.next();
				if (component.isRemovable())
					iterator.remove();
				else
					component.update(dt);
			}
		}

		for (Collision contact : contacts) {
			contact.A.getBounds().translate(Vector.scale(contact.normal, 0.1 * contact.depth));
			if (contact.B instanceof Entity)
				((Entity) contact.B).getBounds().translate(Vector.scale(contact.normal, -0.1 * contact.depth));
		}
	}

	//clipping setColor setFont
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
							if (component.isOpaque() && camera.focuses(component)) {
								int zindex = component.getLayer(); //TODO depth by layer
								component.getBounds().translate((1 + 0.05 * zindex) * -camera.getX(), (1 + 0.05 * zindex) * -camera.getY());
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
			
			// for (Component component : layer) {
			// 	if (component.isOpaque()) {
			// 		int zindex = component.getLayer();
			// 		component.getBounds().translate((1 + 0.05 * zindex) * -camera.getX(), (1 + 0.05 * zindex) * -camera.getY());
			// 		component.render(graphics);
			// 		component.getBounds().translate((1 + 0.05 * zindex) * camera.getX(), (1 + 0.05 * zindex) * camera.getY());
			// 	}
			// }
		}
	}
}