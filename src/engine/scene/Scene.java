package engine.scene;

import engine.scene.entity.Component;
import engine.scene.entity.Drawable;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.awt.Graphics2D;

public class Scene extends Canvas implements Drawable {
    private Camera camera;
    private Map<Integer, Collection<Component>> gameObjects = new TreeMap<Integer, Collection<Component>>();

    public Scene() {}

    public void add(Component... components) {
        for (Component component : components) {
            if (component != null) {
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