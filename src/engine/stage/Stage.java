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
public final class Stage extends Window implements Runnable {
    /**
     * The map of scenes that contain graphical {@code Component}.
     */
    private Map<String, Scene> scenes = new Hashtable<String, Scene>();

    /**
     * The current scene's name in the map.
     */
    private Scene currentScene = null;

    /**
     * The instance of this singleton.
     */
    private static Stage instance = null;

    private Stage() {
    }

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
        currentScene = scenes.get(name);
        return true;
    }

    /**
     * Gets the scene currently displayed.
     * 
     * @return the current scene
     * @see setScene(String name)
     */
    public Scene getScene() {
        return currentScene;
    }

    @Override
    public void run() {
        final float TARGET_UPS = 60;
        final float DELTA_TIME = 1000 / TARGET_UPS;
        final float MAX_ACCUMULATOR = 5 * DELTA_TIME;

        float fps, alpha, accumulator = 0;
        long currentTime, frameStart = System.nanoTime();
        while (true) {
            currentTime = System.nanoTime();
            accumulator += (currentTime - frameStart) / 1000000;
            if (currentTime - frameStart > 1000) {
                fps = 1000000000 / (currentTime - frameStart);
                currentScene.getCamera().updateFPS(fps);
            }
            frameStart = currentTime;

            if (accumulator > MAX_ACCUMULATOR)
                accumulator = MAX_ACCUMULATOR;

            while (accumulator >= DELTA_TIME) {
                currentScene.update(DELTA_TIME);
                accumulator -= DELTA_TIME;
            }

            alpha = accumulator / DELTA_TIME;
            currentScene.clear();
            // currentScene.render(currentScene.getContext());
            // currentScene.CL_render(currentScene.CL_getContext());
            currentScene.CL_render();
            currentScene.show();
        }
    }

    public void start() {
        if (currentScene != null) {
            Thread thread = new Thread(this, "game_loop_thread");
            thread.setDaemon(true);
            thread.start();
        }
    }

    private String deepcopy = "";

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
}