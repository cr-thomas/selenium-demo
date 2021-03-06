package com.webstaurant.selenium.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the class that contains the utility methods to manage property
 * files.
 * 
 * @author ctho50
 * @version 2.0
 */
public class PropertyUtils {

	private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

	private final Properties myConfigProperties = new Properties();

	private PropertyUtils(String propFile) {
		InputStream inputResource = ClassLoader.getSystemResourceAsStream(propFile);
		try {
			myConfigProperties.load(inputResource);
			closeResource(inputResource);
		} catch (IOException ioe) {
			logger.debug("IO Exception", ioe);
		} finally {
			closeResource(inputResource);
		}
	}

	public static Properties getPropertiesByPrefix(Properties props, String[] prefix) {
		Properties returnProps = new Properties();
		Enumeration<String> en = (Enumeration<String>) props.propertyNames();
		while (en.hasMoreElements()) {
			String propName = en.nextElement();
			String propValue = props.getProperty(propName);

			for (String pre : prefix) {
				if (propName.startsWith(pre)) {
					String key = propName.substring(pre.length() + 1);
					returnProps.setProperty(key, propValue);
				}
			}
		}
		return returnProps;
	}

	public static Properties getPropertiesFromMap(Map map) {
		Properties props = new Properties();
		props.putAll(map);
		return props;
	}

	public static Properties combineProperties(Properties props, Properties... moreProps) {
		Properties returnProps = props;
		for (Properties p : moreProps) {
			returnProps.putAll(p);
		}
		return returnProps;
	}

	public static Properties loadPropertiesFromFile(String path) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(path);
			props.load(in);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return props;
	}

	public static void writePropertiesToFile(Properties props, String path) {
		// properties are not alpha sorted, so putting in hashmap to allow the
		// sort before print.
		TreeMap<String, String> propsMap = new TreeMap();
		propsMap.putAll(props.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString())));
		try (Writer writer = Files.newBufferedWriter(Paths.get(path))) {
			propsMap.forEach((key, value) -> {
				try {
					writer.write(key + "=" + value + System.lineSeparator());
				} catch (IOException ex) {
					throw new UncheckedIOException(ex);
				}
			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public static void closeResource(InputStream inputResource) {
		try {
			inputResource.close();
		} catch (IOException ioe) {
			logger.error("IO Exception {}", ioe.getMessage());
		}
	}

	public static PropertyUtils getInstance(final String propFile) {
		return new PropertyUtils(propFile);
	}

	public String getProperty(final String key) {
		return myConfigProperties.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return myConfigProperties.stringPropertyNames();
	}

	public boolean containsKey(final String key) {
		return myConfigProperties.containsKey(key);
	}

}
