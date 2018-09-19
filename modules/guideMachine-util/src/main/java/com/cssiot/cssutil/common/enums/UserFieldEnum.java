package com.cssiot.cssutil.common.enums;


/**
 * 登陆者信息
 * @author
 *	2018-04-28 athena 创建
 */
public enum UserFieldEnum {
    USERNAME("0", "用户名称", "userName"),
    PHONE("1", "手机号", "phone"),
    EMAIL("2", "邮箱", "email"),
    QQ("3", "QQ", "qqNickName"),
    MICROMESSAGE("4", "微信", "wechatNickName"),
    NAILNO("5", "钉钉", "nailNickName"),
    DEPARTMENT("6", "部门", "departmentName"),
    POSITION("7", "主要职务", "positionName"),
    COMPANY("8", "公司" ,"companyName"),
    ACCOUNT("9","账户", "account"),
    RECIPIENTPARTY("10","收款方", "recipientParty"),
    BANKOFDEPOSIT("11","开户行", "bankOfDeposit"),
    WORKNO("12","工号", "workNo")

    ;

    private String code;

    private String message;

    private String fieldName; //模型的字段名
    
    UserFieldEnum(String code, String message, String fieldName) {
        this.code = code;
        this.message = message;
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

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 校验登陆者信息是否存在
	 * @param UserField 包括信息
	 * @return
	 * @author 
	 *	2018-04-28 athena 创建
	 */
	 public static boolean checkUserField(String UserField){
		boolean flag=false;
		for(UserFieldEnum operationTypeEnum:UserFieldEnum.values()){
			if(operationTypeEnum.getCode().equals(UserField)){
				flag=true;
			}
		}
		return flag;
	}
}