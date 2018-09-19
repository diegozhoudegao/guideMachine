package com.cssiot.cssutil.common.enums;


/**
 * 第三方登录的数据库对应字段
 * @author
 *	2018-1-11 tck 创建
 */
public enum ThirdLoginFieldEnum {
	WECHAT("wechat", "wechatThirdLoginId", "wechatNickName"),
	QQ("qq", "qqThirdLoginId", "qqNickName"),
    WEIBO("weibo", "weiboThirdLoginId", "weiboNickName"),
    DINGDING("dingding", "nailNo", "nailNickName"),
    ;

	// 第三方登录类型中类型相同
    private String thirdLoginType;

    // 第三方登录的账号的数据库字段
    private String thirdLoginAcctField;
    
    // 第三方登录账号的昵称的数据库字段
    private String thirdLoginNickNameField;

    ThirdLoginFieldEnum(String thirdLoginType, String thirdLoginAcctField, String thirdLoginNickNameField) {
        this.thirdLoginType = thirdLoginType;
        this.thirdLoginAcctField = thirdLoginAcctField;
        this.thirdLoginNickNameField = thirdLoginNickNameField;
    }

	public String getThirdLoginType() {
		return thirdLoginType;
	}

	public String getThirdLoginAcctField() {
		return thirdLoginAcctField;
	}

	public String getThirdLoginNickNameField() {
		return thirdLoginNickNameField;
	}

	public void setThirdLoginType(String thirdLoginType) {
		this.thirdLoginType = thirdLoginType;
	}

	public void setThirdLoginAcctField(String thirdLoginAcctField) {
		this.thirdLoginAcctField = thirdLoginAcctField;
	}

	public void setThirdLoginNickNameField(String thirdLoginNickNameField) {
		this.thirdLoginNickNameField = thirdLoginNickNameField;
	}

}