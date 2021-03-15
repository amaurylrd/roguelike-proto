package com.engine.scene.image;

import java.awt.image.BufferedImage;

import com.engine.application.Ressources;

public class Sprite {
	/**
	 * Contains the frames for this {@code Sprite}.
	 */
	private Frame[] frames;

	/**
	 * The current frame of this {@code Sprite}.
	 */
	private int currentFrame = 0;

	/**
	 * The reading direction of the animation.
	 * If equals -1, the animation will be reversed.
	 */
	private int direction = 1;

	/**
	 * Tells if this sprite is looping or not.
	 */
	private boolean looping = false;

	/**
	 * Crops the spritesheet in sub-images.
	 * 
	 * @param spritesheet the name of the file which contains all the sprites
	 * @param spriteWidth the width of a sprite in this {@code spritesheet}
	 * @param spriteHeight the height of a sprite in this {@code spritesheet}
	 * @param length the number of sprites in this {@code spritesheet}
	 */
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

	/**
	 * Returns the table of {@code Frame}.
	 * 
	 * @return all the frames of this {@code Sprite}
	 */
	public Frame[] getFrames() {
		return frames;
	}

	/**
	 * Returns the current {@code Frame} of this {@code Sprite}.
	 * 
	 * @return the current {@code Frame}
	 */
	public Frame getCurrentFrame() {
		return frames[currentFrame];
	}

	/**
	 * Retrives the frame at the specified {@code index}.
	 * 
	 * @param index the specified index
	 * @return the frame if it exists, <i>null</i> otherwise
	 */
	public Frame getFrame(int index) {
		return index >= 0 && index < frames.length - 1 ? frames[index] : null;
	}
}
