package com.cssiot.cssutil.common.enums;

/**
 * 打印单据类型
 * @author
 *	2018-05-22 athena 创建
 */
public enum PrintBillTypeEnum {
    FORM("Form","表单"),
    REPORT("Report", "报表"),
    ;

    private String code;

    private String message;

    PrintBillTypeEnum(String code, String message) {
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
	 * 打印单据类型是否存在
	 * @param type 类型
	 * @return
	 * @author 
	 *	2018-05-22 athena 创建
	 */
	 public static boolean checkDataType(String type){
		boolean flag=false;
		for(PrintBillTypeEnum operationTypeEnum:PrintBillTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}