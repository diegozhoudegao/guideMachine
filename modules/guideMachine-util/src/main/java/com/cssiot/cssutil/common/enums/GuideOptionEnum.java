package com.cssiot.cssutil.common.enums;

/**
 * 管理员登录指引操作模块的中英文名
 * @ClassName: GuideOptionEnum
 * @Description: 
 * @author 
 * 2018年4月23日  tck 创建
 */
public enum GuideOptionEnum {
	department("组织架构", "department", "0", "37", " select count(id) from Department where state<>'1' and customerId=:customerId and companyId=:companyId", "1"),
	position("职务", "position", "1", "38", " select count(id) from Position where state<>'1' and customerId=:customerId ", "0"),
	role("角色", "role", "1", "34" ," select count(id) from Role where state<>'1' and customerId=:customerId ", "0"),
	msgTemplate("消息模版", "msgTemplate", "1", "46", " select count(id) from MessageTemplate where state<>'1' and customerId=:customerId ", "0"),
	scheme("方案", "scheme", "0", "74", " select count(id) from Scheme where state<>'1' and createCustomerId=:customerId ", "0"),
	flow("流程", "flow", "0", "70", " select count(id) from Flow where state<>'1' and customerId=:customerId ", "0"),
	dataDictionary("数据字典", "dataDictionary", "0", "39", " select count(id) from DataDictionary where state<>'1' and customerId=:customerId ", "0"),
	baseData("基础数据", "baseData", "0", "65", " select count(id) from Form where state<>'1' and schemeId='2' and isChildForm<>'0' and customerId=:customerId ", "0"),
	singleData("单独表单", "singleData", "0", "76", " select count(id) from Form where state<>'1' and schemeId='1' and isChildForm<>'0' and customerId=:customerId ", "0"),
	;
	
	private String cnName;			//中文名
	private String enName;			//英文名	
	private String isOneCheck;		//是否单选
	private String permissionId;	//权限的id,为了查图标
	private String sql;				//查询的sql
	private String paramType;		//param参数类型,0为客户,1为客户+公司
	private GuideOptionEnum(String cnName, String enName, String isOneCheck, String permissionId, String sql, String paramType) {
		this.cnName = cnName;
		this.enName = enName;
		this.isOneCheck = isOneCheck;
		this.permissionId = permissionId;
		this.sql = sql;
		this.paramType = paramType;
	}
	public String getCnName() {
		return cnName;
	}
	public String getEnName() {
		return enName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getIsOneCheck() {
		return isOneCheck;
	}
	public void setIsOneCheck(String isOneCheck) {
		this.isOneCheck = isOneCheck;
	}
	public String getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(String permissionId) {
		this.permissionId = permissionId;
	}
	public String getSql() {
		return sql;
	}
	public String getParamType() {
		return paramType;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}
}
