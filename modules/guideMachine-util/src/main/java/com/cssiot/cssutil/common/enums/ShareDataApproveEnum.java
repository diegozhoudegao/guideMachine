package com.cssiot.cssutil.common.enums;


/**
 * 共享数据的审核状态
 * @author
 *	2018-03-31 tck新建
 */
public enum ShareDataApproveEnum {
    ABLE("0", "审核通过"),
    ENABLE("1", "未审核通过"),
    SELFUSE("2", "新建自己使用"),
    ;

    private String code;

    private String message;

    ShareDataApproveEnum(String code, String message) {
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
		for(ShareDataApproveEnum operationTypeEnum:ShareDataApproveEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}