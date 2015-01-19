package com.consultancygrid.trz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.consultancygrid.trz.base.Constants;
import com.consultancygrid.trz.base.LabelsConstants;

/**
 * 
 * 
 * Cache Util for all resources used by the project
 * @author Anton Palapeshkov  (Jul 4, 2014)
 *
 * <br><b>History:</b> <br>
 * Jul 4, 2014 Anton Palapeshkov  created <br>
 */
public class ResourceLoaderUtil {

	private static Map<String, String> config  = new HashMap<String, String>();
	
	private static Map<String, String> labels  = new HashMap<String, String>();
	
	
	private ResourceLoaderUtil(){
		
	}
	
	public static String getLabels(String key) throws IOException {
		return getLabels().get(key);
	}
	
	public static String getConfig(String key) throws IOException {
		return getConfig().get(key);
	}
	
	public static Map<String, String> getLabels() throws IOException {

		if (labels.isEmpty()) {
			initLabels();
		}
		return labels;
	}
	
	public static Map<String, String> getConfig() throws IOException {

		if (config.isEmpty()) {
			initConfig();
		}
		return config;
	}
	
	
	private static void initLabels() throws IOException {
		Properties labelsProps = PropertyTools.loadProperties(LabelsConstants.PATH);
		for (Map.Entry<Object, Object> entry : labelsProps.entrySet()) {
			labels.put((String) entry.getKey(), (String) entry.getValue());
		}
	}
	
	private static void initConfig() throws IOException {
		Properties environmentProps = PropertyTools.loadProperties(Constants.CUSTOM_CFG_PATH);
		for (Map.Entry<Object, Object> entry : environmentProps.entrySet()) {
			config.put((String) entry.getKey(), (String) entry.getValue());
		}
	}
	
}
