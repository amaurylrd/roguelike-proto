package com.engine.application;

import java.lang.reflect.Constructor;

public abstract class Plateform {
	/**
	 * Command line arguments.
	 */
	public static String[] parameters;

	/**
	 * Set to true to see debug print.
	 */
	public static final boolean debug = true;

	/**
	 * This method is called by the standalone launcher.
	 * It must not be called more than once or an exception will be thrown.
	 *
	 * @param appClass application class
	 * @param args command-line arguments
	 */
	public static void launchApplication(Class<? extends Application> appClass, String[] args) {
		Plateform.parameters = args == null ? new String[] {} : args;
		try {
			Constructor<? extends Application> constructor = appClass.getConstructor();
			try {
				Application app = constructor.newInstance();
				app.setup();
			} catch (Throwable exception) {
				throw new RuntimeException("Error: Failed to create and initialize a new instance of the constructor's declaring class : " + appClass + ".", exception);
			}
		} catch (NoSuchMethodException | SecurityException exception) {
			throw new RuntimeException("Error: Unable to construct the application instance : " + appClass + ".", exception);
		}
	}

	/**
	 * Prints the log {@code message} is this {@code Plateform} debug mod is on.
	 * 
	 * @param message the message to print to console
	 */
	public static void trace(String message) {
		if (debug) {
			System.out.println(message);
		}
	}
}