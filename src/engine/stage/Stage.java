package engine.stage;

import engine.scene.Scene;

import java.util.Map;
import java.util.Hashtable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * The class {@code Stage} is the top level container of the application.
 */
@SuppressWarnings("serial")
public class Stage extends Window {
    private Map<String, Scene> scenes = new Hashtable<String, Scene>();
    private String currentScene = "";
    private String deepcopy = "";
    private static Stage instance = null;

    private Stage() {}

    public static Stage create() {
        if (instance == null)
            instance = new Stage();
        return instance;
    }

    /**
     * Associates the specified scene with the specified key in the map of scene.
     * 
     * @param name the specified key
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
        return currentScene == "" || !scenes.containsKey(currentScene) ? null : scenes.get(currentScene);
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream(deepcopy);
            try {
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject((Serializable) this);
                oos.flush();
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

    // getScene().clear();
    //                 getScene().render(getScene().getBuffer());
    //                 getScene().show();

    private volatile boolean running = false;
    private volatile boolean paused = false;
    public Thread thread = new Thread(new Runnable() {
        public void run() {
            
            
            running = true;
            while (running) {
                System.nanoTime();
                if (!paused) {
                    Scene sc = getScene();
                    sc.clear();
                    sc.update(0.2f);
                    sc.render(sc.getContext());
                    sc.show();
                }
            }
        }

        public synchronized void stop() {
            running = false;
            try {
			    thread.join();
		    } catch (InterruptedException exception) {
                throw new RuntimeException("Error: Fails to join, the main loop has been interrupted by another thread.", exception);
		    }
        }
        
        public void pause() {
            paused = !paused;
        }
    });

    
}