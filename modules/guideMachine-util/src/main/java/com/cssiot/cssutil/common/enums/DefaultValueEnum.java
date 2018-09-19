package com.cssiot.cssutil.common.enums;


/**
 * 默认值(0当天，1当月第一天，2当月最后一天，3当年第一天，4当年最后一天)
 * @author
 *	2018-03-07 athena 创建
 */
public enum DefaultValueEnum {
    CUSDAY("0", "当天"),
    CUSMONDYONE("1", "当月第一天"),
    CUSMONDYLAST("2", "当月最后一天"),
    CUSYEARONE("3", "当年第一天"),
    CUSYEARLAST("4", "当年最后一天"),

    ;

    private String code;

    private String message;

    DefaultValueEnum(String code, String message) {
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
	 * 校验默认值是否存在
	 * @param defaultValue 默认值
	 * @return
	 * @author 
	 *	2018-03-07 athena 创建
	 */
	 public static boolean checkDataType(String defaultValue){
		boolean flag=false;
		for(DefaultValueEnum operationTypeEnum:DefaultValueEnum.values()){
			if(operationTypeEnum.getCode().equals(defaultValue)){
				flag=true;
			}
		}
		return flag;
	}
}