package com.cssiot.cssutil.common.enums;

/**
 * 游客类型枚举
 * @author 
 * 	2018-10-14 Diego.zhou 新建
 *
 */
public enum VisitorsTypeEnum {
	NORMAL("0","普通游客"),
	VIP("1","VIP"),
	;
	private String code;
	private String message;
	private VisitorsTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}
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
	
}
