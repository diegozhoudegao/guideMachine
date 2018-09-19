package com.cssiot.cssutil.common.enums;
/**
 * 操作枚举
 * @author
 *	2018-07-20 shinry创建
 */
public enum OperationReportEnum {
	EXPORT_OPTION("exportOp","导出","/report/doApplicationReFormExportInfo/","export"),
	SELECT_OPTION("selectOp","查询","/report/doSelectBizReportInfo/","select"),
	;
	private OperationReportEnum(String operateEnName, String operateCnName,String url,String operation) {
		this.operateCnName = operateCnName;
		this.operateEnName = operateEnName;
		this.url=url;
		this.operation=operation;
	}
	private String operateCnName;
	private String operateEnName;
	private String url;
	private String operation;
    public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getOperateCnName() {
		return operateCnName;
	}
	public void setOperateCnName(String operateCnName) {
		this.operateCnName = operateCnName;
	}
	public String getOperateEnName() {
		return operateEnName;
	}
	public void setOperateEnName(String operateEnName) {
		this.operateEnName = operateEnName;
	}

}
