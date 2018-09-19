package com.cssiot.cssutil.common.enums;

/**
 * 数据类型(0:业务表单，1:基础表单)，当值类型为2:数据联动，3:关联表单时才会有值
 * @author
 *	2018-03-23 athena 创建
 *	2018-03-30 athena 数据类型增加自身表单，只有关联表单才可能为自身表单
 */
public enum DataTypeEnum {
    BIZFORM("0", "业务表单"),
    BASFORM("1", "基础表单"),
    SELFFORM("2", "自身表单"),
    SYSFORM("3", "系统表单"),//为数据源使用
    TASKFORM("4", "任务表单"),//为数据源使用
    DATASOURCE("5", "数据源"),//为数据源使用
    ;

    private String code;

    private String message;

    DataTypeEnum(String code, String message) {
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
	 * 校验数据类型是否存在
	 * @param defaultType 值类型
	 * @return
	 * @author 
	 *	2018-03-23 athena 创建
	 */
	 public static boolean checkDataType(String defaultType){
		boolean flag=false;
		for(DataTypeEnum operationTypeEnum:DataTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(defaultType)){
				flag=true;
			}
		}
		return flag;
	}
}