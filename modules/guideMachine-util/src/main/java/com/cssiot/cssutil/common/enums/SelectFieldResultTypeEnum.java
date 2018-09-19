package com.cssiot.cssutil.common.enums;


/**
 * 表单显示字段的结果类型,0序号,1公式
 * @ClassName: FieldInFormTypeEnum
 * @Description: 
 * @author 
 * 2018年6月27日  tck 创建
 */
public enum SelectFieldResultTypeEnum {
	NUMBER("0", "序号"),
    EXPRESSION("1", "公式"),
    ;

    private String code;

    private String message;

    SelectFieldResultTypeEnum(String code, String message) {
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