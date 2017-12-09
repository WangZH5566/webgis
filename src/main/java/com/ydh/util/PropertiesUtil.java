package com.ydh.util;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件读取
 * 
 * @author 
 *
 */
public class PropertiesUtil {

	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static Map<String, ResourceBundle> properties = new HashMap<String, ResourceBundle>();
	
	/**
	 * 将文件读取到Map中
	 * @param name
	 * @return
	 */
	private static ResourceBundle load(String name){
		ResourceBundle resourceBundle = properties.get(name);
		try {
			if(resourceBundle == null){
				//如果properties文件放在classpath的根目录下,那么只需要文件名称,如果在字目录下,那么需要加上文件路径
				Windows下:
				resourceBundle = ResourceBundle.getBundle("\\config\\props\\"+name);
				//Linux下:
				//resourceBundle = ResourceBundle.getBundle("/config/props/"+name);
				properties.put(name, resourceBundle);
			}
		} catch (Exception e) {
			logger.warn("the file " + name +".properties was not found");
		}
		return resourceBundle;
	}

	/**
	 * 根据完整的限定类名和属性的名称获取配置的属性值
	 * @param propertyName
	 * @param key
	 * @return String
	 */
	public static String get(String propertyName, String key) {
		return load(propertyName).getString(key);
		
	}
}
