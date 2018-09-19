package com.cssiot.cssutil.common.enums;

/**
 * 日期格式枚举
 * @author 
 * 	2018-07-27 Diego.zhou 新建
 *
 */
public enum DateFormatEnum {
	FORMAT0("0","yyyy-MM-dd"),
	FORMAT1("1","yyyy-MM-dd HH:mm:ss"),
	FORMAT2("2","yyyy"),
	FORMAT3("3","yyyy-MM"),
	FORMAT4("4","yyyy-MM-dd HH:mm"),
	;
	private String key;
    private String message;
	private DateFormatEnum(String key, String message) {
		this.key = key;
		this.message = message;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}
