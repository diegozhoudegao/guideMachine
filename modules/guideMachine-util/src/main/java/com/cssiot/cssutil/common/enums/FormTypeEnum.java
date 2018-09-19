package com.cssiot.cssutil.common.enums;
/**
 * 自定义行操作枚举
 * @author
 *	2018-6-12 shinry 创建
 */
public enum FormTypeEnum {
	LEAVEABLANK("0","留空"),
	SETFIXVALUE("1","设为固定值"),
	SETUPFORMULA("2","设为公式"),
	SUMCURPAGE("3","当前页求和"),
	SUMCOUNTPAGE("4","所有页求和"),
	AVERAGECURPAGE("5","当前页求平均"),
	AVERAGECOUNTPAGE("6","所有页求平均"),
	MAXCURPAGE("7","当前页求最大值"),
	MAXCOUNTPAGE("8","所有页求最大值"),
	MINCURPAGE("9","当前页求最小值"),
	MINCOUNTPAGE("10","所有页求最大值");
	
	private String code;

    private String message;

    FormTypeEnum(String code, String message) {
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
