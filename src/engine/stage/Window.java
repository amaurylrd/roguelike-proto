package engine.stage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.Rectangle;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class Window extends JFrame implements WindowFocusListener {
    public Window() {
        super();
        setBounds(Screen.getBounds());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addWindowFocusListener(this);
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
}
