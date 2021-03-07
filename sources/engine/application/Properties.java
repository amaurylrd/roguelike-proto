package engine.application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The class {@code Properties} retrives all the application's properties.
 */
public final class Properties {
	/**
	 * The map of properties for this {@code Application}.
	 */
	private static java.util.Properties properties = null;

	/**
	 * The name of the configurations file at the root of this project.
	 */
	private final static String CONFIGURATION_PROPERTIES_FILE = "config.properties";
	
	private Properties() {}

	/**
	 * Loads the property list.
	 * 
	 * @throws NullPointerException if the property file did not exist
	 * @throws IOException on I/O errors while reading, opening or closing the stream
	 */
	public static void load() {
		if (properties == null) {
			Plateform.trace("Debug: " + Properties.class.getName() + " loads properties from " + CONFIGURATION_PROPERTIES_FILE + ".");
			try {
				InputStream propertyList = new FileInputStream(CONFIGURATION_PROPERTIES_FILE);
				properties = new java.util.Properties();
				try {
					properties.load(propertyList);
				} catch (NullPointerException exception) {
					throw new RuntimeException("Error: Property file " + CONFIGURATION_PROPERTIES_FILE + " not found in the classpath.", exception);
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
	 * Puts in the map of properties the specified {@code name} to the specified {@code value}.
	 * 
	 * @param name the specified key property to add or replace
	 * @param value the specified value for this key
	 */
	public static void store(String name, String value) {
		if (properties != null)
			properties.setProperty(name, value);
	}

	/**
	 * Returns a {@code String} representing this property list.
	 * 
	 * @return the property list
	 */
	public static String properties() {
		return properties != null ? properties.toString() : "{}";
	}

	/**
	 * Returns the boolean value for the speciefied the property's {@code name}.
	 * 
	 * @param name the specified property to evaluate
	 * @return the boolean value
	 */
	public static boolean evaluate(String name) {
		return Boolean.parseBoolean(Properties.property(name));
	}

	/**
	 * Saves the property map to the property file.
	 * 
	 * @throws IOException on I/O errors while reading, opening or closing the stream
	 */
	public static void save() {
		try {
			FileOutputStream propertyList = new FileOutputStream(CONFIGURATION_PROPERTIES_FILE);
			properties.store(propertyList, null);
			propertyList.close();
		} catch (IOException exception) {
			throw new RuntimeException("Error: I/O error occurs when trying to open the property file " + CONFIGURATION_PROPERTIES_FILE + ".", exception);
		}
	}
}