package com.cssiot.cssutil.common.enums;


/**
 * ico的名称
 * @ClassName: DefaultIconEnum
 * @Description: 
 * @author 
 * 2018年7月4日  tck 创建
 */
public enum DefaultIconEnum {
	IMGROUP("0", "/upload/20180704default_imGroup.png"),
	PERSON("1", "/upload/20180704default_person.png"),
	TEAM("2", "/upload/20180704default_team.png"),
    ;

    private String code;

    private String message;

    DefaultIconEnum(String code, String message) {
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