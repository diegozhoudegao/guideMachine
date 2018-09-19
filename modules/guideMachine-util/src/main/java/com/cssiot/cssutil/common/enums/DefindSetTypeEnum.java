package com.cssiot.cssutil.common.enums;

/**
 * 设置类型(0:留空,1:设为固定值,2:设为公式,3:当前页求和,4:所有页求和,5:当前页求平均,6:所有页求平均,7:当前页求最大值,8:所有页求最大值,9:当前页求最小值,10:所有页求最小值)
 * 
 * @author
 *	2018-06-28 athena 创建
 */
public enum DefindSetTypeEnum {
    BLANK("0", "留空"),
    FIXEDVALUE("1", "设为固定值"),
    EXPRESSION("2", "设为公式"),//暂不考虑
    CURPAGESUM("3", "当前页求和"),
    ALLPAGESUM("4", "所有页求和"),
    CURPAGEAVG("5", "当前页求平均"),
    ALLPAGEAVG("6", "所有页求平均"),
    CURPAGEMAX("7", "当前页求最大值"),
    ALLPAGEMAX("8", "所有页求最大值"),
    CURPAGEMIN("9", "当前页求最小值"),
    ALLPAGEMIN("10", "所有页求最小值"),

    ;

    private String code;

    private String message;
    
    DefindSetTypeEnum(String code, String message) {
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
	 * 校验组件类型是否存在
	 * @param type 组件类型
	 * @return
	 * @author 
	 *	2018-06-28 athena 创建
	 */
	 public static boolean checkDefindSetType(String type){
		boolean flag=false;
		for(DefindSetTypeEnum operationTypeEnum:DefindSetTypeEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}