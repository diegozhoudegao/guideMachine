package com.cssiot.cssutil.common.enums;
/**
 * 环节类型
 * @author 
 * 	2017-09-20 athena 创建
 */
public enum LinkTypeEnums {
	START("0","开始"),
	END("1","结束"),
	APPLY("2","提出申请"),
	APPRAVE("3","审批"),
	COUNTERSIFN("4","会签"),
	JUDGE("5","判断"),
	ROUTE("6","路由"),
	LINE("7","连线"),
	SCRIPT("8","脚本事件"),
	SUB_PROCESS("9","子流程"),
	TIMER("10","定时事件"),
	MESSAGE("11","消息事件");
	
	
	 private String code;

    private String message;

    LinkTypeEnums(String code, String message) {
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
