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
import sandbox.collission.PlayerTest;
import sandbox.map.Background;

public class Scene extends Canvas implements Drawable {
    private Camera camera;
    private Map<Integer, Collection<Component>> gameObjects = new TreeMap<Integer, Collection<Component>>();
    private Player player;

    public Scene() {
        // add(new TestRectangle(0));
        // TestSprite test = new TestSprite(50, 50, 100, 200, 0);
        // test.sprite = new Sprite("spritetest", 400, 500, 6);
        // add(test);
        
        Entity[] entites = new Entity[4];
        entites[0] = new PlayerTest(10, 500, 100, 100);
        for (int i = 1; i < 4; i++)
            entites[i] = new BoxTest(i*150 + 10, 500, 100, 100);
        add(entites);
        add(new Background(0, 0, 1, 1, -2));
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
    public void update(float dt) {
        if (player != null) {
            Collection<Component> layer = gameObjects.get(Integer.valueOf(player.getLayer()));
            Iterator<Component> iterator = layer.iterator();
            while (iterator.hasNext()) {
                Component component = iterator.next();
                if (component instanceof Collidable && !component.equals(player)) {
                    boolean b = player.collides(component);
                    if (b) {
                        System.out.println(component);
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
        for (Collection<Component> layer : gameObjects.values()) {
            for (Component component : layer) {
                if (component.isOpaque())
                    component.render(graphics);
            }
        }
    }
}