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
    public static class Style {
        public final static String WINDOWED = "WINDOWED";
        public final static String FULLSCREEN = "FULLSCREEN";
        public final static String WINDOWED_FULLSCREEN = "WINDOWED FULLSCREEN";
    }

    public enum State {
        FOREGROUND, BACKGROUND, ICONIFIED
    }

    private Map<String, Runnable> styles = new Hashtable<String, Runnable>();
    private Map<String, Scene> layout = new Hashtable<String, Scene>();
    private Scene scene;

    public Stage() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        addStyle(Style.WINDOWED, () -> {
            setMaximazed();
            setUndecorated(false);
            setExtendedState(JFrame.NORMAL);
        });
        addStyle(Style.FULLSCREEN, () -> {
            setMaximazed();
            setUndecorated(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
        addStyle(Style.WINDOWED_FULLSCREEN, () -> {
            setMaximazed();
            setUndecorated(false);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
        //TODO: dernier style fullsceen + jbar se montre au survol
        //TODO: methode show/setVisible qui revèle le style
    }

    /**
     * Associates a specified action with the specified key in the map of styles.
     * 
     * @param name the specified key
     * @param callback the action to store
     * @throws NullPointerException if the specified key or value is null
     * @see setStyle
     */
    public void addStyle(String name, Runnable callback) {
        styles.put(name, callback);
    }

    public boolean setStyle(String name) {
        if (!isDisplayable() && styles.containsKey(name)) { //trouver comment resize
            styles.get(name).run();
            toFront();
            requestFocusInWindow();
            return true;
        }
        return false;
    }

    //TODO: setState
    public void setState(State state) {
        System.out.println(state);
        /**
         * public void setIconified() {
        setExtendedState(JFrame.ICONIFIED);
    }
         */
    }

    public void setMaximazed() {
        setBounds(Screen.getBounds());
    }

    /**
     * Associates the specified scene with the specified key in the map of scene.
     * 
     * @param name the specified key
     * @param scene the scene to store
     * @throws NullPointerException if the specified key or value is null
     * @see setScene(String name)
     * @see setScene(Scene scene)
     */
    public void addScene(String name, Scene scene) {
        layout.put(name, scene);
    }

    /**
     * Sets the scene which has this specified name.
     * 
     * @param name the specified scene's name
     * @return whether the name is contained in the layout of scene
     * @see getScene
     */
    public boolean setScene(String name) {
        if (!layout.containsKey(name))
            return false;
        scene = layout.get(name);
        return true;
    }

    /**
     * Sets the scene to the specified scene if not null. The scene is not added to the map.
     * 
     * @param scene the specified scene
     * @return whether the scene is null or not
     * @see getScene
     */
    public boolean setScene(Scene scene) {
        if (scene == null)
            return false;
        this.scene = scene;
        return true;
    }

    /**
     * Gets the scene currently displayed.
     * 
     * @return the scene
     * @see setScene
     */
    public Scene getScene() {
        return scene;
    }

    public void sizeToScene() {
        //TODO: size to scene ????
    }
}