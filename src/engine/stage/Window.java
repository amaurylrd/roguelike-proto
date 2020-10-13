package engine.stage;

import javax.swing.JFrame;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

@SuppressWarnings("serial")
public abstract class Window extends JFrame {
    public Runnable onfocus = () -> {};
    public Runnable onblur = () -> {};

    public Window() {
        super();
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                if (onfocus != null)
                    onfocus.run();
            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                if (onfocus != null)
                    onblur.run();
            }
        });
    }

    public void centerOnScreen() {
        Rectangle bounds = Screen.getBounds();
        double clientWidth = bounds.getWidth(), clientHeight =  bounds.getHeight();
        double windowWidth = (double) getWidth(), windowHeight = (double) getHeight();
        double centerX = bounds.getMinX() + (clientWidth - windowWidth) / 2;
        double centerY = bounds.getMinY() + (clientHeight - windowHeight) / 2;
        setLocation(Double.isNaN(centerX) ? 0 : (int) centerX, Double.isNaN(centerY) ? 0 : (int) centerY);
    }
}
