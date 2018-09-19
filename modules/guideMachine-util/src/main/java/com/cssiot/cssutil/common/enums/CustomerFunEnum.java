package com.cssiot.cssutil.common.enums;


/**
 * 客户功能枚举
 * @ClassName: CustomerFunEnum
 * @Description: 
 * @author 
 * 2018年7月3日  tck 创建
 */
public enum CustomerFunEnum {
    IM("1", "即时通讯功能"),
    BIM("2", "BIM功能"),
    ;
	private String code;

    private String message;

    CustomerFunEnum(String code, String message) {
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