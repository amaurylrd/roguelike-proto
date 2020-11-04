package engine.stage.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class K implements KeyListener {
    public static boolean[] buffer;

    public K() {
        buffer = new boolean[10];
    }

    //java doc

    @Override
    public void keyTyped(KeyEvent event) {}

    @Override
    public void keyPressed(KeyEvent event) {
        buffer[event.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        buffer[event.getKeyCode()] = false;
    }
    
}
