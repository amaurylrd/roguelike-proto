package engine.scene;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.awt.Graphics2D;

import engine.scene.entity.Collidable;
import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.scene.entity.Entity;
import engine.scene.entity.Player;
import engine.scene.image.Sprite;
import sandbox.TestRectangle;
import sandbox.TestSprite;
import sandbox.collission.BoxTest;
import sandbox.map.Background;
import engine.physics2d.Force;
import engine.physics2d.Vector;

import sandbox.Input;
public class Scene extends Canvas implements Drawable {
	private Camera camera;
	private Map<Integer, Collection<Component>> gameObjects = new TreeMap<Integer, Collection<Component>>();
	protected Player player;

	public Scene() {
		//player avant
		
		// add(new TestRectangle(0));
		// TestSprite test = new TestSprite(50, 50, 100, 200, 0);
		// test.sprite = new Sprite("spritetest", 400, 500, 6);
		// add(test);

		Component[] entites = new Component[4];
		entites[0] = new Player(10, 500, 100, 100);
		for (int i = 1; i < 2; i++)
			entites[i] = new BoxTest(i*150 + 10, 700, 100, 100, 0);
		add(entites);
		add(new BoxTest(10, 850, 900, 100, 0));
		camera = new Camera(player);
		//add(new Background(0, 0, 1, 1, -2));
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

	public Player p2 = null;

	@Override
	public void update(float dt) {
		if (player != null) {

			int x = Input.isPressed(Input.LEFT) ? 1 : 0;
			int x2 = Input.isPressed(Input.RIGHT) ? 1 : 0;
			player.velocity.setX(x * -10 + x2 * 10);
			
			int y = Input.isPressed(Input.JUMP) ? 1 : 0;
			player.velocity.translateY(-1 * y);

			player.velocity.translateY(Force.GRAVITY * (float) dt);

			p2 = (Player) player.clone();
			p2.update(dt);
			
			Collection<Component> layer = gameObjects.get(Integer.valueOf(player.getLayer()));
			Iterator<Component> iterator = layer.iterator();
			while (iterator.hasNext()) {
				Component component = iterator.next();
				if (component instanceof Collidable && !component.equals(player)) {

					component.TEST = p2.getBounds().intersects(component.getBounds());

					//if c'est un tile
					//else if c'est une entity
					
					// component.TEST = p2.collides((Collidable) component);
					// //System.out.println(component.TEST);
					if (component.TEST) {
						// final Vector normalW = new Vector(0, 1); //SOL
						// final Vector normalH = new Vector(1, 0); //MUR
						final Vector normal = player.getNormal(component);
						player.velocity = player.velocity.sub(normal.scale(player.velocity.dot(normal)));
						//player.velocity = player.velocity.sub(normal.scale(player.velocity.dot(normalH)));
					}
				}
			}
		}

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
	}

	@Override
	public void render(Graphics2D graphics) {

		
		//graphics.translate(-player.getBounds().getX(), -player.getBounds().getY());
		
		for (Collection<Component> layer : gameObjects.values()) {
			for (Component component : layer) {
				if (component.isOpaque())
					component.render(graphics);
			}
		}
		
		//graphics.translate(player.getBounds().getX(), player.getBounds().getY());
		

		// if (p2 != null) {
		// 	java.awt.Color c = graphics.getColor();
		// 	graphics.setColor(java.awt.Color.BLUE);
		// 	graphics.draw(p2.getBounds().stroke());
		// 	graphics.setColor(c);
		// }
	}
}