package engine.scene;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;
import java.util.Map;
import java.awt.Graphics2D;
import static java.util.Arrays.asList;

public class Scene extends Canvas implements Drawable {
    private Camera camera;
    private Map<Integer, Collection<Entity>> gameObjects = new HashMap<Integer, Collection<Entity>>(); 
    //plus tard remplacer la liste par un hierarchy tree
     
    public class Test extends Entity {
        int x = 100;
        int y = 300;
        int w = 500;
        int h = 500;
        int xspeed = 20;
        int yspeed = 30;

        public Test() {
            super(0, 0, 0, 0);
        }

        @Override
        public void update(float dt) {
            x += xspeed * dt;
        }

        @Override
        public void render(Graphics2D graphics) {
            graphics.draw(new java.awt.geom.Rectangle2D.Double(x, y, w, h));
        }
        

    }
    public Scene() {
        add(new Test());
    }

    public void add(Entity... components) {
        for (Entity component : components) {
            Collection<Entity> layer = gameObjects.computeIfAbsent(component.getLayer(), k -> new ArrayList<Entity>());
            layer.add(component);
        }
    }

    public void remove(Entity... components) {
        Collection<Entity> remaining = asList(components);
        for (Collection<Entity> layer : gameObjects.values()) {
            while (!remaining.isEmpty()) {
                for (Entity component : remaining)
                    if (layer.remove(component))
                        remaining.remove(component);
            }
        }
    }

    @Override
    public void update(float dt) {
        for (Collection<Entity> layer : gameObjects.values())
            for (Entity component : layer)
                component.update(dt);
    }

    @Override
    public void render(Graphics2D graphics) {
        //graphics.drawImage(mon iamge);

        //render tile/background <- tree
        //render entites <- une queue

        for (Collection<Entity> layer : gameObjects.values())
            for (Entity component : layer)
                component.render(graphics);
    }
}