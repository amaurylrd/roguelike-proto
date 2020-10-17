package engine.stage;

import java.awt.Rectangle;

@SuppressWarnings("serial")
public abstract class Window extends FocusableWindow {
    public void centerOnScreen() {
        Rectangle bounds = Screen.getBounds();
        double clientWidth = bounds.getWidth(), clientHeight =  bounds.getHeight();
        double windowWidth = (double) getWidth(), windowHeight = (double) getHeight();
        double centerX = bounds.getMinX() + (clientWidth - windowWidth) / 2;
        double centerY = bounds.getMinY() + (clientHeight - windowHeight) / 2;
        setLocation(Double.isNaN(centerX) ? 0 : (int) centerX, Double.isNaN(centerY) ? 0 : (int) centerY);
    }
}
