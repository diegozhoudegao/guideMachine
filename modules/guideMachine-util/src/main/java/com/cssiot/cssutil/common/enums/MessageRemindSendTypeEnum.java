package com.cssiot.cssutil.common.enums;

/**
 * 消息提醒的发送方式
 * @ClassName: TimeUnitEnum
 * @Description: 
 * @author 
 * 2018年8月24日  tck 创建
 */
public enum MessageRemindSendTypeEnum {
	IMMEDIATELY("0","立即发送"),
	ONCE("1","发送一次"),
	REPEAT("2","重复发送")
	;
	private String code;

    private String message;
    
    MessageRemindSendTypeEnum(String code, String message) {
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
	public static MessageRemindSendTypeEnum getByValue(String value) {
        for (MessageRemindSendTypeEnum typeEnum : values()) {  
            if (typeEnum.getCode().equals(value)) {  
                return typeEnum;
            }  
        }  
        return null;  
    } 
}
