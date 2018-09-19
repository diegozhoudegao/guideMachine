package com.cssiot.cssutil.common.enums;
/**
 * 申请范围
 * @author 
 * 	2018-01-12  shinry 创建
 */
public enum RangEnums {
	 PERSON("0","人员"),
	 TEAM("1","组织"),
	 ROLE("2","角色"),
	 DEPARTMENT("3","部门"),
	 POSITION("4","职务"),
	 LOADER("5","直属上级"),
	 FROMlINKLOADER("6","前一环节直属领导"),
	 ALLAPPLYER("-2","所有申请人"),
	 SUBMIT_APPLYER("-3","提出申请人"),
	 CURRAPPLYER("-1","当前审批人");
	
	 ;
	 private String code;

    private String message;

    RangEnums(String code, String message) {
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
