package com.cssiot.cssutil.common.enums;

public enum FormateTypeEums {
	EXCEL("0","excel"),
	WORD("1","word"),
	PDF("2","pdf");
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
	FormateTypeEums(String code, String message) {
		this.code=code;
		this.message=message;
	}
}
