package com.cssiot.cssutil.common.enums;
/**
 * 流向类型
 * @author 
 * 	2018-01-12  shinry 创建
 */
public enum DirectionEnums {
	TEMPORY("0","临时"),
	FORWARD("1","跳转"),
	ORIGINAL("2","原始"),;
	
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
	 DirectionEnums(String code, String message) {
		this.code=code;
		this.message=message;
	}
	

}
