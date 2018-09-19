package com.cssiot.cssutil.common.enums;


/**
 * 字段在什么类型的表单中
 * @ClassName: FieldInFormTypeEnum
 * @Description: 
 * @author 
 * 2018年6月27日  tck 创建
 */
public enum FieldInFormTypeEnum {
    MAINFORM("0", "主表单"),
    SLAVEFORM("1", "从表单"),
    NEWADDFIELD("2", "新增字段"),
    ;

    private String code;

    private String message;

    FieldInFormTypeEnum(String code, String message) {
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
		for(FieldInFormTypeEnum operationTypeEnum:FieldInFormTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}