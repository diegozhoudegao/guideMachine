package com.cssiot.cssutil.common.utils;

import java.util.Map;
import com.google.common.collect.Maps;

/**
 * 全局配置类
 * @author athena
 * @time 2016-02-04
 * 
 */
public class Global {
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader;
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key,String propertieName) {
		String value = map.get(key);
		if (value == null){
			propertiesLoader = new PropertiesLoader(propertieName+".properties");
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}
	
}
