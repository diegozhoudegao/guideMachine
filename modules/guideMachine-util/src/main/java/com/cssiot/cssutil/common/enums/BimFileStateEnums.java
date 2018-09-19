package com.cssiot.cssutil.common.enums;

public enum BimFileStateEnums {
	UPLOADSUCCESS("0","上传成功"),
	PREPARETRANSLATE("1","待转换"),
	PROCESSINGTRANSLATE("2","转换中"),
	SUCCESSTRANLATE("3","转换成功"),
	FAILDTRANSLATE("4","转换失败"),
	;
	
	   private String code;

	    private String message;

	    BimFileStateEnums(String code, String message) {
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
