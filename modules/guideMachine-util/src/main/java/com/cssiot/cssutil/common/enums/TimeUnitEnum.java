package com.cssiot.cssutil.common.enums;

/**
 * 时间的单位
 * @ClassName: TimeUnitEnum
 * @Description: 
 * @author 
 * 2018年8月24日  tck 创建
 */
public enum TimeUnitEnum {
	MINUTE("0","分"),
	HOUR("1","时"),
	DAY("2","天")
	;
	private String code;

    private String message;
    
    TimeUnitEnum(String code, String message) {
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
	* 遍历一下下
	* @param value
	* @return
	* @author
	* 2018年7月6日 tck 创建
	*/
	public static TimeUnitEnum getByValue(String value) {
        for (TimeUnitEnum typeEnum : values()) {  
            if (typeEnum.getCode().equals(value)) {  
                return typeEnum;
            }  
        }  
        return null;  
    } 
}
