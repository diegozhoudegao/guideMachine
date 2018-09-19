package com.cssiot.cssutil.common.enums;


/**
 * 类型(0是1否)
 * @author
 *	2017-11-25 athena 创建
 */
public enum AbleEnableEnum {
    ABLE("0", "是"),
    ENABLE("1", "否"),
    ;

    private String code;

    private String message;

    AbleEnableEnum(String code, String message) {
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
	 * 校验类型是否存在
	 * @param type 类型
	 * @return
	 * @author 
	 *	2017-11-25 athena 创建
	 */
	 public static boolean checkDataType(String type){
		boolean flag=false;
		for(AbleEnableEnum operationTypeEnum:AbleEnableEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}