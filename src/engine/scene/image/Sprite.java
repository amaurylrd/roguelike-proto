package engine.scene.image;

import engine.application.Ressources;

import java.awt.image.BufferedImage;

public class Sprite {
	private Frame[] frames;
	private int currentFrame = 0;

	/**
	 * The reading direction of the animation.
	 * If equals -1, the animation will be reversed.
	 */
	private int direction = 1;

	private boolean looping = false;

	public Sprite(String spritesheet, int spriteWidth, int spriteHeight, int length) {
		BufferedImage bitmap = Ressources.ressource(spritesheet);
		int columns = (int) Math.ceil(bitmap.getWidth() / spriteWidth);
		frames = new Frame[length];
		for (int i = 0; i < length; ++i) {
			int spriteX = (i % columns) * spriteWidth;
			int spriteY = (i / columns) * spriteHeight;
			frames[i] = new Frame(bitmap.getSubimage(spriteX, spriteY, spriteWidth, spriteHeight), 0, 0);
		}
	}

	public Frame[] getFrames() {
		return frames;
	}

	public Frame getCurrentFrame() {
		return frames[currentFrame];
	}

	public Frame getFrame(int index) {
		return index >= 0 && index < frames.length - 1 ? frames[index] : null;
	}
}
