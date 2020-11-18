package engine.stage;

import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * The class {@code Window} represents a graphical frame.
 * This is an abstract class that cannot be instantiated directly.
 * */
@SuppressWarnings("serial")
public abstract class Window extends JFrame implements WindowFocusListener, ComponentListener {
    /**
     * Constructs a new initially invisible {@code Window}.
     */
    public Window() {
        super();
        setBounds(Screen.getBounds());
        setPreferredSize(getSize());
        setFocusable(true);
        setResizable(false);
        setUndecorated(true);
        setIgnoreRepaint(false);

        setFocusTraversalKeysEnabled(false);
        addWindowFocusListener(this);
        addComponentListener(this);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getRootPane().registerKeyboardAction(event -> {
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    /**
     * Sets the window's location to the center of the main screen.
     */
    public void centerOnScreen() {
        Rectangle bounds = Screen.getBounds();
        double clientWidth = bounds.getWidth(), clientHeight =  bounds.getHeight();
        double windowWidth = (double) getWidth(), windowHeight = (double) getHeight();
        double centerX = bounds.getMinX() + (clientWidth - windowWidth) / 2;
        double centerY = bounds.getMinY() + (clientHeight - windowHeight) / 2;
        setLocation(Double.isNaN(centerX) ? 0 : (int) centerX, Double.isNaN(centerY) ? 0 : (int) centerY);
    }
    
    /**
     * Invoked when the window is set to be the focused Window, which means
     * that the Window, or one of its subcomponents, will receive keyboard
     * events.
     * 
     * @param event the event to be processed
     */
    @Override
    public void windowGainedFocus(WindowEvent event) {}

    /**
     * Invoked when the window is no longer the focused Window, which means
     * that keyboard events will no longer be delivered to the Window or any of
     * its subcomponents.
     * 
     * @param event the event to be processed
     */
    @Override
    public void windowLostFocus(WindowEvent event) {}


    /**
     * Invoked when the component's size changes.
     * 
     * @param event the event to be processed
     */
    @Override
    public void componentResized(ComponentEvent event) {}

    /**
     * Invoked when the component's position changes.
     * 
     * @param event the event to be processed
     */
    @Override
    public void componentMoved(ComponentEvent event) {}

    /**
     * Invoked when the component has been made visible.
     * 
     * @param event the event to be processed
     */
    @Override
    public void componentShown(ComponentEvent event) {}

    /**
     * Invoked when the component has been made invisible.
     * 
     * @param event the event to be processed
     */
    @Override
    public void componentHidden(ComponentEvent event) {}
}
