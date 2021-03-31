package com.engine.stage;

import java.util.Map;
import java.util.Hashtable;
import java.awt.BorderLayout;
import javax.media.opengl.GLProfile;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLException;
import com.engine.graphics2d.Canvas;
import com.engine.scene.Scene;

/**
 * The class {@code Stage} is the top level container of the application.
 */
@SuppressWarnings("serial")
public final class Stage extends Window implements Loopable {
    /**
     * The map of scenes that contain graphical {@code Component}.
     */
    private Map<String, Scene> scenes = new Hashtable<String, Scene>();

    /**
     * The current scene's name in the map.
     */
    private Scene currentScene = null;

    public static final String DEFAULT_SCENE = "";
    /**
     * The main canvas that provides OpenGL rendering support.
     */
    private Canvas canvas = null;

    /**
     * The instance of this singleton.
     */
    private static Stage instance = null;

    /**
     * Constructor of this class. Instantiates the openGL profile and capabilities.
     */
    private Stage() {
        try {
            GLProfile.initSingleton();
            GLProfile profile = GLProfile.get(GLProfile.GL2);
            
            GLCapabilities capabilities = new GLCapabilities(profile);
            capabilities.setRedBits(8);
            capabilities.setGreenBits(8);
            capabilities.setBlueBits(8);
            capabilities.setAlphaBits(8);
            capabilities.setDoubleBuffered(true);
            capabilities.setBackgroundOpaque(true);
            capabilities.setHardwareAccelerated(true);
            capabilities.setNumSamples(4);
            capabilities.setBackgroundOpaque(false);
            capabilities.setSampleBuffers(true);

            canvas = new Canvas(capabilities);
            canvas.setSize(getSize());
            getContentPane().add(canvas, BorderLayout.CENTER);
        } catch (GLException exception) {
            throw new RuntimeException("Error: OpenGL version is not supported.", exception);
        }
    }

    /**
     * Gets the instance of this singleton {@code Stage}.
     * 
     * @return the object instance
     */
    public static final Stage create() {
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
            //scene.bind(this);
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
        canvas.addGLEventListener(currentScene); 
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
        float fps, alpha, accumulator = 0;
        long currentTime, frameStart = System.nanoTime();
        while (true) {
            currentTime = System.nanoTime();
            accumulator += (currentTime - frameStart) / 1000000;
            if (currentTime - frameStart > 1000) {
                fps = 1000000000 / (currentTime - frameStart);
                System.out.println(fps);
                //currentScene.getCamera().updateFPS(fps);
            }
            frameStart = currentTime;

            if (accumulator > MAX_ACCUMULATOR)
                accumulator = MAX_ACCUMULATOR;

            while (accumulator >= DELTA_TIME) {
                currentScene.update(DELTA_TIME);
                accumulator -= DELTA_TIME;
            }

            alpha = accumulator / DELTA_TIME;
            canvas.display();
        }
    }

    //dans une interface :
    // private String deepcopy = "";

    // public void save() {
    //     // TODO: debug saving game
    //     try {
    //         FileOutputStream fos = new FileOutputStream(deepcopy);
    //         try {
    //             ObjectOutputStream oos = new ObjectOutputStream(fos);
    //             System.out.println("write");
    //             oos.writeObject(this);
    //             System.out.println("flush");
    //             oos.flush();
    //             System.out.println("close");
    //             oos.close();
    //         } catch (IOException exception) {
    //             throw new RuntimeException("Error: Occurs when writing the stream header in the file " + deepcopy + ".",
    //                     exception);
    //         }
    //         fos.close();
    //     } catch (IOException exception) {
    //         throw new RuntimeException("Error: Failed to open or close the file " + deepcopy + " for serialiation.",
    //                 exception);
    //     }
    // }

    // private Stage restore() {
    //     Stage proxy = null;
    //     try {
    //         FileInputStream fis = new FileInputStream(deepcopy);
    //         try {
    //             ObjectInputStream ois = new ObjectInputStream(fis);
    //             try {
    //                 proxy = (Stage) ois.readObject();
    //                 ois.close();
    //             } catch (IOException | ClassNotFoundException exception) {
    //                 throw new RuntimeException("Error: Failed to read the serialized object from stream.", exception);
    //             }
    //             fis.close();
    //         } catch (IOException exception) {
    //             throw new RuntimeException("Error: Failed to read the serialized file " + deepcopy + ".", exception);
    //         }
    //     } catch (IOException exception) {
    //         throw new RuntimeException("Error: Failed to open or close the file " + deepcopy + ".", exception);
    //     }
    //     return proxy;
    // }

    // private boolean saveExists() {
    //     return new File(deepcopy).exists();
    // }
}