package com.cssiot.cssutil.common.enums;
/**
 * 建议类型enum
 * @ClassName: FieldInFormTypeEnum
 * @Description: 
 * @author 
 * 2018-07-19 shinry 创建
 */
public enum FeedBackTypeEmum {
	PRODUCTADVICE("0","产品建议"),
	FUNCTIONERROR("1","功能异常"),
	OTHER("2","其他");
	
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
	
	FeedBackTypeEmum(String code, String message) {
		this.code=code;
		this.message=message;
	}
}
