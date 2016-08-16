package com.consultancygrid.trz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Utility class used for manipulation with property files
 * 
 * @author Nikolai Gagov (2008-4-18)
 * 
 * <br>
 *         <b>History:</b> <br>
 *         2008-4-18 Nikolai Gagov created <br>
 */
public class PropertyTools {

	// private static final Log log = LogFactory.getLog(PropertyTools.class);
	private static final Logger log				= Logger.getLogger(PropertyTools.class);

	public static final String  PROPERTIES_VERSION = "properties.version";

	protected PropertyTools() {
	}

	/**
	 * Loads property file by it's name
	 * 
	 * @param propertyFile
	 * @return loaded property file inside Properties object
	 * @throws IOException
	 */
	public static Properties loadProperties(String propertyFile) throws IOException {
		InputStream propsIs = null;
		final Properties props = new Properties();
		try {
			propsIs = getResourceAsStream(propertyFile);
			props.load(propsIs);
		} catch (Exception e) {
			throw new FileNotFoundException("Couldn't find properties '" + propertyFile + "'");
		} finally {
			if (propsIs != null) {
				propsIs.close();
			}
		}
		return props;
	}

	/**
	 * Loads external property file
	 * 
	 * @param propertyFile
	 * @return loaded property file inside Properties object
	 * @throws IOEXception
	 */
	public static Properties loadExternalPropertyFile(String propertyFile) throws IOException {

		InputStream in = null;
		final Properties props = new Properties();

		try {
			in = new FileInputStream(propertyFile);
			props.load(in);
		} catch (FileNotFoundException ex) {
			log.error(ex);
		} finally {
			in.close();
		}

		return props;
	}

	/**
	 * 
	 * Reads the version of a properties file having a name of special version
	 * property. If the version property does not exist in the properties file,
	 * or it is not a number, returns -1
	 * 
	 * @param props
	 *            The properties which version is required
	 * @param versionProperty
	 *            name of version property
	 * @return
	 */

	private static int getPropertiesVersion(Properties props, String versionProperty) {
		int result = -1;
		if (props.getProperty(versionProperty) != null) {
			try {
				result = Integer.parseInt(props.getProperty(versionProperty));
			} catch (NumberFormatException nfe) {
				log.error("Can't parse properties revision", nfe);
			}
		}
		return result;
	}

	/**
	 * See {@link #loadExternalProperties(File, String, String)}
	 * 
	 * @param externalFile
	 * @param defaultPropertiesResource
	 * @return
	 * @throws IOException
	 */
	public static Properties loadExternalProperties(File externalFile, String defaultPropertiesResource) throws IOException {
		return loadExternalProperties(externalFile, defaultPropertiesResource, PROPERTIES_VERSION);
	}

	/**
	 * 
	 * Tries to load a properties from the file system. <br>
	 * 1. If the file doesn't exist, loads the properties from resource, creates
	 * the external file by saving in it the contents of the resource
	 * properties, and returns the loaded from the resources properties.<br>
	 * 2. If the external file does exist, reads the version of both (external
	 * and resource) property files from a special versionProperty.<br>
	 * 2.1. If the version of the external file is smaller than the version of
	 * the resource file, overwrites the external file with the contents of the
	 * resource file and returns the loaded from the resources properties. <br>
	 * 2.2 If the version of the external file is greater than or equal to the
	 * version of the resource file, returns the properties loaded from the
	 * external file<br>
	 * 
	 * See {@link #getPropertiesVersion(Properties, String)} for the algorithm
	 * of reading the versionProperty
	 * 
	 * @param externalFile
	 *            The external file
	 * @param defaultPropertiesResource
	 *            The name of the resource with default properties. The resource
	 *            is loaded using {@link #loadProperties(String)}
	 * @param versionProperty
	 *            The name of the special property that contains the version of
	 *            the properties file. Defaults to {@link #PROPERTIES_VERSION}
	 * @return The loaded properties
	 * @throws IOException
	 */
	public static Properties loadExternalProperties(File externalFile, String defaultPropertiesResource, String versionProperty)
			throws IOException {
		Properties result = loadProperties(defaultPropertiesResource);
		if (externalFile.exists()) {
			Properties fileProperties = new Properties();
			FileInputStream fis = new FileInputStream(externalFile);
			try {
				fileProperties.load(fis);
			} finally {
				fis.close();
			}
			int fileVersion = getPropertiesVersion(fileProperties, versionProperty);
			int resourcePropsVersion = getPropertiesVersion(result, versionProperty);
			log.info(MessageFormat.format("External file version: {0}. Resource file version: {1}", fileVersion,
										  resourcePropsVersion));
			if (fileVersion >= resourcePropsVersion) {
				result = fileProperties;
			} else {
				log.warn("Overwriting external file");
				StringWriter oldProperties = new StringWriter();
				fileProperties.store(oldProperties, null);
				FileOutputStream fos = new FileOutputStream(externalFile);
				try {
					result.store(fos, MessageFormat.format("File overwritten due to newer version appeared in resources.\n"
																   + "Resource used:{0}\n" + "Old properties was:\n{1}",
														   defaultPropertiesResource, oldProperties.toString()));
				} finally {
					fos.close();
				}
			}
		} else {
			log.info("External file doesn't exist");
			FileOutputStream fos = new FileOutputStream(externalFile);
			try {
				result.store(fos, MessageFormat.format("Created new properties using resource {0}", defaultPropertiesResource));
			} finally {
				fos.close();
			}
		}
		return result;
	}

	/**
	 * Loads given resource as Stream by it's resourceName
	 * 
	 * @param resourceName
	 * @return the loaded resource as Stream
	 */
	public static InputStream getResourceAsStream(String resourceName) {
		final String stripped = resourceName.startsWith("/") ? resourceName.substring(1) : resourceName;
		return getResourceAsStream(resourceName, stripped);
	}

	private static InputStream getResourceAsStream(String resourceName, String stripped) {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream stream = null;
		if (classLoader != null) {
			stream = classLoader.getResourceAsStream(stripped);
		}
		if (stream == null) {
			stream = PropertyTools.class.getResourceAsStream(resourceName);
		}
		if (stream == null) {
			stream = PropertyTools.class.getClassLoader().getResourceAsStream(stripped);
		}
		return stream;
	}

	public static URL getResource(String resourceName) {
		final String stripped = resourceName.startsWith("/") ? resourceName.substring(1) : resourceName;
		return getResource(resourceName, stripped);
	}

	private static URL getResource(String resourceName, String stripped) {
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		URL url = null;
		if (classLoader != null) {
			url = classLoader.getResource(stripped);
		}
		if (url == null) {
			url = PropertyTools.class.getResource(resourceName);
		}
		if (url == null) {
			url = PropertyTools.class.getClassLoader().getResource(stripped);
		}
		return url;
	}
}
