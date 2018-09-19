package com.cssiot.cssutil.common.utils.httpclient;

import java.util.Map;

public interface HttpClientCallback {

	Map<String,Object> doConnect(Map<String,Object> retMap);
	
}
