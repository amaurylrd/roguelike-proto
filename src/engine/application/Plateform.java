package engine.application;

import java.lang.reflect.Constructor;

public abstract class Plateform {
	public static String[] parameters;

	public static void launchApplication(Class<? extends Application> appClass, String[] args) {
		Plateform.parameters = args == null ? new String[] {} : args;
		try {
			Constructor<? extends Application> constructor = appClass.getConstructor();
			try {
				Application app = constructor.newInstance();
				app.setup();
			} catch (Throwable exception) {
				throw new RuntimeException("Error: Failed to create and initialize a new instance of the constructor's declaring class : "+appClass+".", exception);
			}
		} catch (NoSuchMethodException | SecurityException exception) {
			throw new RuntimeException("Error: Unable to construct the application instance : "+appClass+".", exception);
		}
    }
}