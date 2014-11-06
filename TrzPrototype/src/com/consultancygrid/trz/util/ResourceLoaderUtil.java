package com.consultancygrid.trz.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

//	private static Map<String, String> environmentConfig  = new HashMap<String, String>();
	
	private static Map<String, String> labels  = new HashMap<String, String>();
	
	
	private ResourceLoaderUtil(){
		
	}
	
	public static String getLabels(String key) throws IOException {
		return getLabels().get(key);
	}
	
//	public static String getEnvrironmentConfig(String key) throws IOException {
//		return getEnvironmentConfig().get(key);
//	}
	
	public static Map<String, String> getLabels() throws IOException {

		if (labels.isEmpty()) {
			initLabels();
		}
		return labels;
	}
	
//	public static Map<String, String> getEnvironmentConfig() throws IOException {
//
//		if (environmentConfig.isEmpty()) {
//			initEnvironmentConfig();
//		}
//		return environmentConfig;
//	}
	
	
	private static void initLabels() throws IOException {
		Properties labelsProps = PropertyTools.loadProperties(LabelsConstants.PATH);
		for (Map.Entry<Object, Object> entry : labelsProps.entrySet()) {
			labels.put((String) entry.getKey(), (String) entry.getValue());
		}
	}
	
//	private static void initEnvironmentConfig() throws IOException {
//		Properties environmentProps = PropertyTools.loadProperties(Constants.ENVIRONMENT);
//		for (Map.Entry<Object, Object> entry : environmentProps.entrySet()) {
//			environmentConfig.put((String) entry.getKey(), (String) entry.getValue());
//		}
//	}
	
}
