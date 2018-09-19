package com.cssiot.cssutil.common.enums;

/**
 * 聚合函数类型
 * @ClassName: GroupByTypeEnum
 * @Description: 
 * @author 
 * 2018年7月16日  tck 创建
 */
public enum GroupByTypeEnum {
	COUNT("0", "计数", "count"),
	SUM("1", "总和", "sum"),
	AVG("2", "平均值", "avg"),
	MAX("3", "最大值", "max"),
	MIN("4", "最小值", "min"),
	;
	
	private String code;

    private String message;
    
    private String enName;

    GroupByTypeEnum(String code, String message, String enName) {
        this.code = code;
        this.message = message;
        this.enName = enName;
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

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}
}
