package com.cssiot.cssutil.common.enums;


/**
 * 任务相关的模块名称(100任务申请信息)
 * @ClassName: ModuleTaskEnum
 * @Description: 
 * @author 
 * 2018年7月9日  tck 创建
 */
public enum ModuleTaskEnum {
    TASKAPPLY("100", "任务申请", "t_cssflow_run_taskApply"),
    ;

    private String code;//100起始,接在ModuleTypeEnum后面,其实这些都是formId

    private String message;

    private String tableName;

    ModuleTaskEnum(String code, String message, String tableName) {
        this.code = code;
        this.message = message;
        this.tableName = tableName;
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

	/**
	 * 校验模块名称是否存在
	 * @param moduleType 模块名称
	 * @return
	 * @author 
	 *	2018-03-08 athena 创建
	 */
	 public static boolean checkDataType(String moduleType){
		boolean flag=false;
		for(ModuleTaskEnum operationTypeEnum:ModuleTaskEnum.values()){
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
	public static ModuleTaskEnum getByValue(String value) {
        for (ModuleTaskEnum typeEnum : values()) {  
            if (typeEnum.getCode().equals(value)) {  
                return typeEnum;
            }  
        }  
        return null;  
    } 
}