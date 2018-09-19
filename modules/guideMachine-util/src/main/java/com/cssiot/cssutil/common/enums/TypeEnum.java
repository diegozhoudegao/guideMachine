package com.cssiot.cssutil.common.enums;


/**
 * 方案类型(0是1否)
 * @author
 *	2018-01-05 athena 创建
 */
public enum TypeEnum {
    PUBLIC("0", "公开"),
    PRIVATE("1", "私有"),
    ;

    private String code;

    private String message;

    TypeEnum(String code, String message) {
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
	 *	2018-01-05 athena 创建
	 */
	 public static boolean checkDataType(String type){
		boolean flag=false;
		for(TypeEnum operationTypeEnum:TypeEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}