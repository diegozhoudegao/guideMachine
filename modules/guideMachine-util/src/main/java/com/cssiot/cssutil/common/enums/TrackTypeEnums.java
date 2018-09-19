package com.cssiot.cssutil.common.enums;
/**
 * 流向类型枚举
 * @author 
 * 	2018-01-12  shinry 创建
 */
public enum TrackTypeEnums {
	THISTOCHILE("0","主流程到子流程"),
	CHILEFLOWINNER("1","子流程内部"),
	CHILETOTHIS("2","子流程到主流程"),
	THISFLOWINNER("3","子流程内部"),
	CHILDTOCHILE("4","子流程到子流程");
	 private String code;

    private String message;

    TrackTypeEnums(String code, String message) {
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
