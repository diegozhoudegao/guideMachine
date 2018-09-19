package com.cssiot.cssutil.common.enums;


/**
 * 值类型(0:自定义，1:公式，2:数据联动（一个组件关联表，其他组件从关联表中带出数据），3:关联表单（一个组件关联表单，自定义表单），4:系统数据（一个组件关联表单，基础权限表单），5:表单内关联)
 * @author
 *	2018-03-07 athena 创建
 *	2018-04-27 athena 增加登陆者信息
 */
public enum DefaultTypeEnum {
    CUSTOM("0", "自定义"),
    FORMULA("1", "公式"),
    DATALINKAGE("2", "数据联动"),
    ASSFORM("3", "关联表单"),
    SYSTEMDATA("4", "系统数据"),
    FORMINASS("5", "表单内关联"),
    USERFIELD("6", "登陆者信息"),

    ;

    private String code;

    private String message;

    DefaultTypeEnum(String code, String message) {
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
	 * 校验值类型是否存在
	 * @param defaultType 值类型
	 * @return
	 * @author 
	 *	2018-03-07 athena 创建
	 */
	 public static boolean checkDataType(String defaultType){
		boolean flag=false;
		for(DefaultTypeEnum operationTypeEnum:DefaultTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(defaultType)){
				flag=true;
			}
		}
		return flag;
	}
}