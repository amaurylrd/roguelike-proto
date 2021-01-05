package engine.stage;

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import engine.application.Plateform;
import engine.scene.Scene;

/**
 * The class {@code Stage} is the top level container of the application.
 */
@SuppressWarnings("serial")
public final class Stage extends Window {
    private Map<String, Scene> scenes = new Hashtable<String, Scene>();
    private String currentScene = "";
    private String deepcopy = "";

    /**
     * The instance of this singleton.
     */
    private static Stage instance = null;

    private Stage() {}

    /**
     * Gets the instance of this singleton {@code Stage}.
     * 
     * @return the object instance
     */
    public static Stage create() {
        if (instance == null)
            instance = new Stage();
        return instance;
    }

    /**
     * Associates the specified scene with the specified key in the map of scene.
     * 
     * @param name  the specified key
     * @param scene the scene to store
     * @see setScene(String name)
     */
    public void addScene(String name, Scene scene) {
        if (name != null && scene != null) {
            scenes.put(name, scene);
            scene.bind(this);
        }
    }

    /**
     * Sets the scene which has this specified name.
     * 
     * @param name the specified scene's name
     * @return whether the name is contained in the layout of scene
     * @see getScene()
     */
    public boolean setScene(String name) {
        if (!scenes.containsKey(name))
            return false;
        currentScene = name;
        return true;
    }

    /**
     * Gets the scene currently displayed.
     * 
     * @return the current scene
     * @see setScene(String name)
     */
    public Scene getScene() {
        return !scenes.containsKey(currentScene) ? null : scenes.get(currentScene);
    }

    public void save() {
        //TODO: debug saving game
        try {
            FileOutputStream fos = new FileOutputStream(deepcopy);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                System.out.println("write");
                oos.writeObject(this);
                System.out.println("flush");
                oos.flush();
                System.out.println("close");
                oos.close();
            } catch (IOException exception) {
                throw new RuntimeException("Error: Occurs when writing the stream header in the file " + deepcopy + ".", exception);
            }
            fos.close();
        } catch (IOException exception) {
            throw new RuntimeException("Error: Failed to open or close the file " + deepcopy + " for serialiation.", exception);
        }
    }

    private Stage restore() {
        Stage proxy = null;
        try {
            FileInputStream fis = new FileInputStream(deepcopy);
            try {
                ObjectInputStream ois = new ObjectInputStream(fis);
                try {
                    proxy = (Stage) ois.readObject();
                    ois.close();
                } catch (IOException | ClassNotFoundException exception) {
                    throw new RuntimeException("Error: Failed to read the serialized object from stream.", exception);
                }
                fis.close();
            } catch (IOException exception) {
                throw new RuntimeException("Error: Failed to read the serialized file " + deepcopy + ".", exception);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Error: Failed to open or close the file " + deepcopy + ".", exception);
        }
        return proxy;
    }

    private boolean saveExists() {
        return new File(deepcopy).exists();
    }

    private volatile boolean running = false;
    private volatile boolean paused = false;
    public Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            /*final int TARGET_FPS = 60;
            final long RENDER_SPEED = 1000000000 / TARGET_FPS;

            //long lastRenderTime = System.nanoTime();
            Plateform.trace("Debug: The stage is now running the game loop.");
            running = true;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {}
            long lastUpdateTime = System.nanoTime();
            
            while (running) {
                //TODO: if  (!paused)
                Scene scene = getScene();
                // System.out.println("chatte" + scene.player.getBounds().getLocation());
                long currentTime = System.nanoTime();
                long updateTime = currentTime - lastUpdateTime;
                float elapsed = updateTime/(float) RENDER_SPEED;
                System.out.println(elapsed);
                scene.update(elapsed);


                scene.clear();
                // long renderTime = currentTime - lastRenderTime;
                // scene.getContext().drawString(""+renderTime/100000, 50, 50);
                // scene.getContext().drawString(""+updateTime/100000, 50, 100);
                // lastRenderTime = System.nanoTime();
                scene.render(scene.getContext());
                scene.show();
                lastUpdateTime = currentTime;
            }
            try {
                thread.join();
            } catch (InterruptedException exception) {
                throw new RuntimeException("Error: Fails to join, the main loop has been interrupted by another thread.", exception);
            }


            */
            final int TARGET_FPS = 60;
            final float RENDER_SPEED = 120 / 1000000000;
            final float displayPeriod = 1000000000 / TARGET_FPS;
            long now = System.nanoTime();
            boolean running = true;
            while (running) {
                Scene scene = getScene();
                long elapsed, lastFrameTime = System.nanoTime();
                long lastUpdateTime = System.nanoTime();
                do 
                {
                    now = System.nanoTime();
                    scene.update((float) (now - lastUpdateTime) * RENDER_SPEED);
                    lastUpdateTime = now;
                    elapsed = now - lastFrameTime;
                } while (elapsed < displayPeriod);
                scene.clear();
                scene.render(scene.getContext());
                scene.show();
            }
        }

        public void stop() {
            running = false;
        }

        public void pause() {
            paused = !paused;
        }
    });
}