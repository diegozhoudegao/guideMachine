package com.cssiot.cssutil.common.utils;

import javax.servlet.http.HttpServletRequest;

public class GetIp {
	
	public static String getIpAddr(HttpServletRequest request){ 
	    try{
			String ip = request.getHeader("x-forwarded-for");  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("PRoxy-Client-IP");  
		    }  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getHeader("WL-Proxy-Client-IP");  
		    }  
		    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
		        ip = request.getRemoteAddr();  
		    }if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {   
		        ip = request.getRemoteAddr();   
		    }
		    return ip;  
	    }catch(Exception e){
	    	return "获取ip失败";
	    }
	}  
	
}
