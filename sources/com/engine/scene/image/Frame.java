package com.engine.scene.image;

import java.awt.image.BufferedImage;

public class Frame {
	private BufferedImage image;
	private int delay; //ms
	private int duration; //ms
	
	//public Polygon hurtbox
	//public Cercle hitbox[]
	//invincibility

	/**
	 * 
	 * Lag indicates periods when a character is busy initiating or finishing a performed move, being left unable to perform any other action.
	 */
	public enum Type {
		STARTUP, ACTIVE, LAG
	}

	private Type type = Type.ACTIVE;

	public Frame(BufferedImage image, int delay, int duration) {
		this.image = image;
		this.delay = delay;
		this.duration = duration;
	}
}
