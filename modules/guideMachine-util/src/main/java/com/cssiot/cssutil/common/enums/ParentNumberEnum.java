package com.cssiot.cssutil.common.enums;

/**
 * 基础资料中父级编码枚举
 * @ClassName: ParentNumberEnum
 * @Description: 
 * @author 
 * 2018年1月22日  tck 创建
 */
public enum ParentNumberEnum {

    FIRSTCOMPANYPARENTNUM("00", "一级公司的父编码"),
    ;

    private String code;

    private String message;

    ParentNumberEnum(String code, String message) {
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