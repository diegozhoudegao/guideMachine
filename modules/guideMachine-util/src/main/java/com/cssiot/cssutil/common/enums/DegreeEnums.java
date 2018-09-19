package com.cssiot.cssutil.common.enums;
/**
 * 紧急程度enums
 * @author 
 * 	2018-01-12  shinry 创建
 */
public enum DegreeEnums {
	NOMAL("1","正常"),
	HURRY("2","加急"),
	SPECIAL("3","特急");
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
	DegreeEnums(String code, String message) {
		this.code=code;
		this.message=message;
	}
}
