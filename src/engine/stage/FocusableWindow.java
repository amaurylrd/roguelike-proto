package engine.stage;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class FocusableWindow extends JFrame implements WindowFocusListener {
    /**
     * The action to run when the window gains focus 
    */
    public Runnable onfocus = () -> {};

    /**
     * The action to run when the window loses focus.
     */
    public Runnable onblur = () -> {};

    public FocusableWindow() {
        super();
        addWindowFocusListener(this);
    }

    /**
     * Invoked when the window is set to be the focused Window, which means
     * that the Window, or one of its subcomponents, will receive keyboard
     * events.
     * 
     * @param event the event to be processed
     */
    @Override
    public void windowGainedFocus(WindowEvent event) {
        handle(event, true);
    }

    /**
     * Invoked when the window is no longer the focused Window, which means
     * that keyboard events will no longer be delivered to the Window or any of
     * its subcomponents.
     * 
     * @param event the event to be processed
     */
    @Override
    public void windowLostFocus(WindowEvent event) {
        handle(event, false);
    }

    /**
     * Runs the callback runnable whenever the window focus state changes.
     * 
     * @param event the event to be processed
     * @param focus true if the window has the focus, false otherwise
     */
    public void handle(WindowEvent event, boolean focus) {
        Runnable action = focus ? onfocus : onblur;
        if (action != null)
            action.run();
    }
}
