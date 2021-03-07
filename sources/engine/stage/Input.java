package engine.stage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {
	public static boolean[] keyPressed;

	public final static int JUMP = KeyEvent.VK_SPACE;
	public final static int LEFT = KeyEvent.VK_LEFT;
	public final static int RIGHT = KeyEvent.VK_RIGHT;
	
	public Input() {
		keyPressed = new boolean[255];
		java.util.Arrays.fill(keyPressed, false);
	}

	public static boolean isPressed(int key) {
		return keyPressed[key];
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		keyPressed[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keyPressed[e.getKeyCode()] = false;
	}	
}
