package engine.scene;

import engine.scene.entity.Component;
import engine.scene.entity.Drawable;
import engine.scene.entity.particle.Particle;
import sandbox.TestParticle;
import sandbox.TestRectangle;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.awt.Graphics2D;

public class Scene extends Canvas implements Drawable {
    private Camera camera;
    private Map<Integer, Collection<Component>> gameObjects = new TreeMap<Integer, Collection<Component>>();
    //plus tard peut-être remplacer la liste par un hierarchy tree si besoin d'exprimer des dépendances

    public Scene() {
        add(new TestRectangle(-2), new TestRectangle(1));
    }

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
                Collection<Component> layer = gameObjects.get(key);
                List<Component> list = Collections.synchronizedList(new ArrayList<Component>(layer));
                synchronized (list) {
                    Iterator<Component> iterator = list.iterator();
                    while (iterator.hasNext() && !component.equals(iterator.next())) {
                        iterator.remove();
                        if (list.isEmpty())
                            gameObjects.remove(key);
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {
        
        for (int i = 0; i < 3; i++)
            add(new TestParticle());
        
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