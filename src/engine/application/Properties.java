package engine.application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The class {@code Properties} retrives all the application's properties.
 */
public final class Properties {
	private static java.util.Properties properties = null;

	private Properties() {}

	/**
	 * Loads the property list.
	 */
	public static void load() {
		if (properties == null) {
			final String propertyFile = "config.properties";
			Plateform.trace("Debug: " + Properties.class.getName() + " loads properties from " + propertyFile + ".");
			try {
				InputStream propertyList = new FileInputStream(propertyFile);
				properties = new java.util.Properties();
				try {
					properties.load(propertyList);
				} catch (NullPointerException exception) {
					throw new RuntimeException("Error: Property file " + propertyFile + " not found in the classpath.", exception);
				}
				propertyList.close();
			} catch (IOException exception) {
				throw new RuntimeException("Error: Fails to read the property list when reading from the input stream.", exception);
			}
		}
	}

	/**
	 * Searches for the property with the specified {@code name} in this property list.
	 * The method returns <i>null</i> if the property is not found.
	 * 
	 * @param name the specified property name
	 * @return the value of the property
	 */
	public static String property(String name) {
		return properties == null ? null : properties.getProperty(name);
	}

	/**
	 * Returns a {@code String} representing this property list.
	 * 
	 * @return the property list
	 */
	public String properties() {
		return properties.toString();
	}
}