package com.cssiot.cssutil.common.enums;
/**
 * jsontype
 * @author 
 * 	2018-01-12  shinry 创建
 */
public enum JsonTypeEnums {
	SL_TYPE("sl","直线"),
	LR_TYPE("lr","Z型折现"),
	TBN_TYPE("tbn","形折线"),;
	private String code;
	private String message;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	JsonTypeEnums(String code, String message) {
		this.code=code;
		this.message=message;
	}
	
}
