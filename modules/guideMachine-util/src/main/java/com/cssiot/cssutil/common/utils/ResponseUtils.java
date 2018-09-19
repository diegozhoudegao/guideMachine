package com.cssiot.cssutil.common.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 提供了针对easyui响应的相关工具类
 * 
 * @author Administrator
 *
 */
public class ResponseUtils {

    /**
     * 将对象转换为json字符串
     * yyyy-MM-dd HH:mm:ss
     * @param object
     * @return json字符串
     */
    public static final String toJSONString(Object object) {
        SerializeWriter out = new SerializeWriter();
        try {
            JSONSerializer serializer = new JSONSerializer(out);
            serializer.config(SerializerFeature.WriteDateUseDateFormat, true);
            serializer.config(SerializerFeature.DisableCircularReferenceDetect, true);
            serializer.setDateFormat(DateUtil.YYYYMMDDHHMMSS_19);
//            serializer.getPropertyFilters().add(HibernatePropertyFilter.getInstance());
            serializer.write(object);
            return out.toString();
        } finally {
            out.close();
        }
    }

    /**
     * 返回json字符串，格式为 {result : true/false}
     * 
     * @param result
     *            结果成功与否
     * @return json字符串
     */
    public static String buildResultJson(Boolean result) {
        return buildResultJson(result, null);
    }

    /**
     * 返回json字符串，格式为 {result : true/false,message : ''}
     * 
     * @param result
     *            结果成功与否
     * @param message
     *            信息描述
     * @return json字符串
     */
    public static String buildResultJson(Boolean result, String message) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", result);
        jsonObj.put("message", message);
        return jsonObj.toJSONString();
    }
    public static String buildResultJson(Boolean result, String message,String url) {
    	JSONObject jsonObj = new JSONObject();
    	jsonObj.put("result", result);
    	jsonObj.put("message", message);
    	jsonObj.put("url", url);
    	return jsonObj.toJSONString();
    }
    
    /**
     * 返回json字符串，格式为 {result : '自定义判断字符串',message : '自定义的消息提示'}
     * 
     * @param result
     *           自定义判断字符串
     * @param message
     *            信息描述
     * @return json字符串
     */
    public static String buildResultJson(String result, String message) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", result);
        jsonObj.put("message", message);
        return jsonObj.toJSONString();
    }
    
    /**
     * map转json字符串
     * 
     * @param map
     * @return json字符串
     */
    public static String buildResultJson(Map<String, Object> map) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.putAll(map);
        return jsonObj.toJSONString();
    }
    
    
    /**
     * 实体对象转json字符串
     * 
     * @param map
     * @return json字符串
     */
    public static String buildResultJson(Object obj) {
        return JSON.toJSONString(obj);
    }
    
    /**
     * 从json 表达式中获取一个map
     * @param jsonString json字符串
     * @return
     * @author athena
	 * @time 2016-03-22
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMapJson(String jsonString) {
    	net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(jsonString);
        Iterator keyIter = jsonObject.keys();
        String key;
        Object value;
        Map valueMap = new HashMap();  
        while (keyIter.hasNext()) {
            key = (String) keyIter.next();
            value = jsonObject.get(key);
            valueMap.put(key, value);
        }
        return valueMap;
    }

}
