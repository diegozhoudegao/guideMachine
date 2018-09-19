package com.cssiot.cssutil.common.enums;

/**
 * 状态
 * @author
 *	2017-10-22 athena 创建
 *	2017-12-27 athena 增加权限的3和4，用户的99状态，且增加说明
 *	2018-02-27 Diego.zhou 增加审批状态
 *	2018-04-11 athena 将禁用改为暂停
 */
public enum StateEnum {

    NEWSTATE("0", "新建"),//权限中的0为除admin外其他使用(仅admin不能使用) 20171227 athena
    DELETESTATE("1", "删除"),
    DISABLESTATE("2", "暂停"),
    FILESTATE("3", "归档"),
    ADMINONLYSTATE("3", "仅admin显示"),//权限中的3为权限管理模块，即不希望在角色赋权限时显示，但是admin形成树菜单需要(仅仅admin使用) 20171227 athena
    USABLESTATE("4", "都可用"),//权限中的4为权限管理模块，即希望在角色赋权限时显示，且admin形成树菜单需要(不仅仅admin使用，其他也能使用) 20171227 athena
    ADMINSTATE("99", "admin"),//用户中的99为admin 20171227 athena
    APPROVING("3","审批中"),//审批状态
    APPROVECOMPLETE("4","审批完成"),//审批状态
    APPROVEFILED("5","审批归档"),//审批状态
    ACCEPT("6","采纳"),
    REFUST("7","拒绝"),
    TEMPSTATE("3", "暂存"),//作为业务数据的暂存状态使用
    
    ;

    private String code;

    private String message;

    StateEnum(String code, String message) {
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
    
}