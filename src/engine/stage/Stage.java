package engine.stage;

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
        // TODO: debug saving game
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
                throw new RuntimeException("Error: Occurs when writing the stream header in the file " + deepcopy + ".",
                        exception);
            }
            fos.close();
        } catch (IOException exception) {
            throw new RuntimeException("Error: Failed to open or close the file " + deepcopy + " for serialiation.",
                    exception);
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
            final double TARGET_UPS = 60;
            final double DELTA_TIME = 1000 / TARGET_UPS;
            double alpha, accumulator = 0;

            long frameStart = System.nanoTime();
            Scene scene = getScene();
            while (true) {
                long currentTime = System.nanoTime();
                accumulator += (currentTime - frameStart) / 1000000;
                frameStart = currentTime;

                if (accumulator > 50)
                    accumulator = DELTA_TIME;

                while (accumulator >= DELTA_TIME) {
                    scene.update(DELTA_TIME);
                    accumulator -= DELTA_TIME;
                }
            
                alpha = accumulator / DELTA_TIME;
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