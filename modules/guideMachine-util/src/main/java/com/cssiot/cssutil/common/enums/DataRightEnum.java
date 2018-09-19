package com.cssiot.cssutil.common.enums;


/**
 * 数据权限(0:所有用户，1:当前用户，2:当前及以下用户)
 * @author
 *	2018-03-07 athena 创建
 */
public enum DataRightEnum {
    ALLUSER("0", "所有用户"),
    CURUSER("1", "当前用户"),
    BEFUSER("2", "当前及以下用户"),

    ;

    private String code;

    private String message;

    DataRightEnum(String code, String message) {
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
	 * 校验数据权限是否存在
	 * @param dataRight 数据权限
	 * @return
	 * @author 
	 *	2018-03-07 athena 创建
	 */
	 public static boolean checkDataType(String dataRight){
		boolean flag=false;
		for(DataRightEnum operationTypeEnum:DataRightEnum.values()){
			if(operationTypeEnum.getCode().equals(dataRight)){
				flag=true;
			}
		}
		return flag;
	}
}