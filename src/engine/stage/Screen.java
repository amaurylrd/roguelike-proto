package engine.stage;

import java.awt.Toolkit;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsConfiguration;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.HeadlessException;

/**
 * The class {@code Screen} describes the characteristics of a graphics destination such as monitor.
 * In a virtual device multi-screen environment in which the desktop area could span multiple physical screen devices, the bounds of the Screen objects are relative to the Screen.primary.
 */
public final class Screen {
    private Screen() {}

    /**
     * Returns all the screen devices's configuration.
     *
     * @return the array of configuration
     */
    public static GraphicsConfiguration[] getScreensConfiguration() {
        GraphicsDevice[] devices = Screen.getScreens();
        GraphicsConfiguration[] configurations = new GraphicsConfiguration[devices.length];
        for (int i = 0; i < devices.length; i++)
            configurations[i] = devices[i].getConfigurations()[0];
        return configurations;
    }
    
    /**
     * Returns all the screen devices.
     *
     * @return the array of screen
     */
    public static GraphicsDevice[] getScreens() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
    }

    /**
     * Returns the primary screen.
     * 
     * @return the screen
     */
    public static GraphicsDevice getPrimaryScreen() {
        return Screen.getScreensConfiguration()[0].getDevice();
    }

    /**
     * Returns the default screen {@code GraphicsDevice} of the local {@code GraphicsEnvironment}.
     * 
     * @return the default screen
     * @throws HeadlessException - if {@link isHeadless()} returns {@value true}
     */
    public static GraphicsDevice getDefaultScreen() {
        try {
            GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            return screen;
        } catch (HeadlessException exception) {
            throw new RuntimeException("Error: Environnement does not support mouse, keyboard or display.", exception);
        }
    }

   /**
    * Returns the inner bounds of the {@code GraphicsConfiguration} of the primary screen.
    *
    * @return the bounds of the primary screen without task bar
    */
    public static Rectangle getInnerBounds() {
        GraphicsDevice screen = Screen.getPrimaryScreen();
        GraphicsConfiguration configuration = screen.getDefaultConfiguration();
        Insets insets = Toolkit.getDefaultToolkit().getScreenInsets(configuration);
        Rectangle bounds = configuration.getBounds();
        bounds.x += insets.left;
        bounds.y += insets.top;
        bounds.width -= (insets.left + insets.right);
        bounds.height -= (insets.top + insets.bottom);
        return bounds;
    }

    /**
     * Returns the bounds of the {@code GraphicsConfiguration} of the primary screen.
     * 
     * @return the bounds of the primary screen
     */
    public static Rectangle getBounds() {
        GraphicsDevice screen = Screen.getPrimaryScreen();
        return screen.getDefaultConfiguration().getBounds();
    }

    /**
     * Gets the resolution of this screen.
     * 
     * @return The resolution of the primary screen
     */
    public static double getScale() {
        Rectangle bounds = Screen.getBounds();
        return bounds.height/bounds.width;
    } 
}