package com.cssiot.cssutil.common.utils.httpclient;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class HttpClientTemplate {

	public static JSONObject connect(HttpClientCallback action){
		Map<String,Object> retMap =new HashMap<String,Object>(3);
		retMap=action.doConnect(retMap);
		JSONObject json=new JSONObject();
		if("0".equals(retMap.get("flag").toString())){
			json=JSONObject.fromObject(retMap.get("value"));
		}else{
			if("3".equals(retMap.get("flag").toString())&&"404".equals(retMap.get("statusCode").toString())){
				json.accumulate("code", 4007);
				json.accumulate("message", "请确认服务器是否启动");
			}else{
				json.accumulate("code", 4007);
				json.accumulate("message", "请求异常，请重试");
			}
		}
		return json;
	}
	
}
