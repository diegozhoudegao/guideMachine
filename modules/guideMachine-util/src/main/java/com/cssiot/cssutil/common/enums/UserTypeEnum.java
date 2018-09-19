package com.cssiot.cssutil.common.enums;


/**
 * 用户类型(0系统管理员1管理员2普通用户)
 * @author
 *	2017-11-25 athena 创建
 */
public enum UserTypeEnum {
    SYSADMIN("0", "系统管理员"),
    ADMIN("1", "管理员"),
    NORMALUSER("2", "普通用户"),

    ;

    private String code;

    private String message;

    UserTypeEnum(String code, String message) {
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
    
	/**
	 * 校验用户类型是否存在
	 * @param userType 用户类型
	 * @return
	 * @author 
	 *	2017-11-25 athena 创建
	 */
	 public static boolean checkDataType(String userType){
		boolean flag=false;
		for(UserTypeEnum operationTypeEnum:UserTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(userType)){
				flag=true;
			}
		}
		return flag;
	}
}