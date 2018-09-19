package com.cssiot.cssutil.common.enums;


/**
 * 模块名称(数据字典+0用户、5人员、3部门、2角色、1讨论组、6公司、4职务)
 * @author
 *	2018-03-08 athena 创建
 */
public enum ModuleTypeEnum {
    USER("0", "用户", "t_sys_user", "userName_"),
    TEAM("1", "讨论组", "t_sys_team", "name_"),
    ROLE("2", "角色" , "t_sys_role", "name_"),
    DEPARTMENT("3", "部门", "t_sys_department", "name_"),
    POSITION("4", "职务", "t_sys_position", "name_"),
    PERSON("5", "人员", "t_sys_person", "userName_"),
    COMPANY("6", "公司", "t_sys_company", "name_"),

    ;

    private String code;
    private String message;
    private String tableName;
    private String fieldName;

    ModuleTypeEnum(String code, String message, String tableName, String fieldName) {
        this.code = code;
        this.message = message;
        this.tableName = tableName;
        this.fieldName = fieldName;
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
	
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 校验模块名称是否存在
	 * @param moduleType 模块名称
	 * @return
	 * @author 
	 *	2018-03-08 athena 创建
	 */
	 public static boolean checkDataType(String moduleType){
		boolean flag=false;
		for(ModuleTypeEnum operationTypeEnum:ModuleTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(moduleType)){
				flag=true;
			}
		}
		return flag;
	}
    
	/**
	* 遍历一下下
	* @param value
	* @return
	* @author
	* 2018年7月6日 tck 创建
	*/
	public static ModuleTypeEnum getByValue(String value) {
        for (ModuleTypeEnum typeEnum : values()) {  
            if (typeEnum.getCode().equals(value)) {  
                return typeEnum;
            }  
        }  
        return null;  
    } 
}