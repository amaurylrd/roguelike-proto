package engine.stage;

import javax.swing.JFrame;
import java.util.Map;
import java.util.Hashtable;

import engine.scene.Scene;

/**
 * The class {@code Stage} is the top level container of the application.
 */
@SuppressWarnings("serial")
public class Stage extends Window {
    public enum Style {
        WINDOWED, FULLSCREEN, WINDOWED_FULLSCREEN;
    }

    private final Map<Style, Runnable> STAGE_STYLES = new Hashtable<Style, Runnable>();

    /**
     * Applies a default style to the window if it is not displayed.
     * 
     * @param style the name of the style
     * @return <i>true</i> if the style could be applied, <i>false</i> otherwise
     */
    public boolean setStyle(Style style) {
        if (!isDisplayable() && STAGE_STYLES.containsKey(style)) {
            STAGE_STYLES.get(style).run();
            toFront();
            requestFocusInWindow();
            return true;
        }
        return false;
    }

    /**
     * Associates a specified action with the specified key in the map of styles.
     * 
     * @param name the specified key
     * @param callback the action to store
     * @throws NullPointerException if the specified key or value is <i>null</i>
     * @see setStyle
     */
    protected void addStyle(Style name, Runnable callback) {
        STAGE_STYLES.put(name, callback);
    }

    private Map<String, Scene> scenes = new Hashtable<String, Scene>();
    private Scene currentScene;

    public Stage() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addStyle(Style.WINDOWED, () -> {
            setBounds(Screen.getBounds());
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
        });

        addStyle(Style.FULLSCREEN, () -> {
            setBounds(Screen.getBounds());
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });

        addStyle(Style.WINDOWED_FULLSCREEN, () -> {
            setBounds(Screen.getBounds());
            setUndecorated(false);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }

    /**
     * Associates the specified scene with the specified key in the map of scene.
     * 
     * @param name the specified key
     * @param scene the scene to store
     * @throws NullPointerException if the specified key or value is <i>null</i>
     * @see setScene(String name)
     * @see setScene(Scene scene)
     */
    public void addScene(String name, Scene scene) {
        scenes.put(name, scene);
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
     * Sets the scene to the specified scene if not <i>null</i>.
     * The scene is not added to the map.
     * 
     * @param scene the specified scene
     * @return whether the scene is <i>null</i> or not
     * @see getScene()
     */
    public boolean setScene(Scene scene) {
        if (scene == null)
            return false;
        currentScene = scene;
        return true;
    }

    /**
     * Gets the scene currently displayed.
     * 
     * @return the scene
     * @see setScene(String name)
     * @see setScene(Scene scene)
     */
    public Scene getScene() {
        return currentScene;
    }
}