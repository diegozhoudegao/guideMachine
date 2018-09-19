package com.cssiot.cssutil.common.enums;

/**
 * 一些表结构里的字段名称
 * @ClassName: TableFieldNameEnum
 * @Description: 
 * @author 
 * 2018年7月6日  tck 创建
 */
public enum TableFieldNameEnum {
	USERNAME("userName_","姓名"),
	PHONE("phone_","手机号"),
	EMAIL("email_","邮箱"),
	ACCOUNT("account_","账户"),
	RECIPIENTPARTY("recipientParty_","收款方"),
	BANKOFDEPOSIT("bankOfDeposit_","开户行"),
	WORKNO("workNo_","工号"),
	NAME("name_","名称"),
	FLOWCNNAME("flowCnName_","流程中文名称"),
	COMPLETETIME("completeTime_","完成时间"),
	STATE("state_","状态"),
    ;

    private String tableField;

    private String cnName;

    TableFieldNameEnum(String tableField, String cnName) {
        this.tableField = tableField;
        this.cnName = cnName;
    }

	public String getTableField() {
		return tableField;
	}

	public String getCnName() {
		return cnName;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}