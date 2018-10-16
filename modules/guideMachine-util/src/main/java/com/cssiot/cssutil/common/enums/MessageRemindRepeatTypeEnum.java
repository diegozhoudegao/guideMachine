package com.cssiot.cssutil.common.enums;

/**
 * 消息提醒的重复方式
 * @ClassName: TimeUnitEnum
 * @Description: 
 * @author 
 * 2018年8月24日  tck 创建
 */
public enum MessageRemindRepeatTypeEnum {
	EVERYDAY("0","每日"),
	EVERYWEEK("1","每周"),
	EVERYMONTH("2","每月"),
	EVERYYEAR("3","每年"),
	;
	private String code;

    private String message;
    
    MessageRemindRepeatTypeEnum(String code, String message) {
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
	public static MessageRemindRepeatTypeEnum getByValue(String value) {
        for (MessageRemindRepeatTypeEnum typeEnum : values()) {  
            if (typeEnum.getCode().equals(value)) {  
                return typeEnum;
            }  
        }  
        return null;  
    } 
}
