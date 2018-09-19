package com.cssiot.cssutil.common.enums;
/**
 * 方案应用枚举
 * @author 
 * 	2018-01-12  shinry 创建
 */
public enum ApplicationEnums {
	 APPLY("0", "应用"),
	 NOAPPLY("1", "未应用"),;
	 private String code;

	    private String message;

	    ApplicationEnums(String code, String message) {
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
