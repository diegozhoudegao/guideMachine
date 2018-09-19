package com.cssiot.cssutil.common.enums;


/**
 * 第三个登录返回字段
 * @author
 *	2018-1-11 tck 创建
 */
public enum ThirdLoginReturnEnum {
	ISBINDTHIRDACCT("isBindThirdAcct", "是否绑定了第三方账号"),
	ISBINDCUSTOMER("isBindCustomer", "是否已绑定该客户公司"),
	ISLOGINSUCCESS("isLoginSuccess", "是否直接登录成功"),
	ISUNBINDTHIRDACCT("isUnBindThirdAcct", "解绑第三方账号"),
	THIRDLOGINACCT("thirdLoginAcct", "第三方账号"),
	CUSTOMERLIST("customerList", "客户信息下拉"),
	THIRDLOGINMODEL("thirdLoginModel", "第三方账号模型"),
	USERANDCUSTOMERMODEL("userAndCustomerModel", "用户和客户模型"),
	LOGINUSERMODEL("loginUserModel", "登录模型"),
	CUSTOMERSHOWMODEL("customerShowModel", "客户下拉模型"),
	PHONE("phone", "电话号码"),
    ;

    private String code;

    private String message;

    ThirdLoginReturnEnum(String code, String message) {
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
		for(ThirdLoginReturnEnum operationTypeEnum:ThirdLoginReturnEnum.values()){
			if(operationTypeEnum.getCode().equals(type)){
				flag=true;
			}
		}
		return flag;
	}
}