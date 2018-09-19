package com.cssiot.cssutil.common.enums;
/**
 *bim类型
 * @author
 *	2018-03-08 athena 创建
 */
public enum OffLinkTypeEnum {
	 TRANSLATE("0","装换","files"),
	 INTEGRATEID("1","集成","integrations"),
	 COMPAREID("2","模型对比","comparisions");
	 private String code;

    private String message;
    private String type;

    OffLinkTypeEnum(String code, String message,String type) {
        this.code = code;
        this.message = message;
        this.type=type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
