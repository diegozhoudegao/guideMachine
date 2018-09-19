package com.cssiot.cssutil.common.data;

import java.util.List;

public class IsEnabledModel {
	private String code;			//返回的code(4008:不可用，1：可用)
	private String message;			//返回的message
	private List dataList;			//list数据
	private Object object;			//通用匹配符
	
	public IsEnabledModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public IsEnabledModel(String code, String message, List dataList, Object object) {
		super();
		this.code = code;
		this.message = message;
		this.dataList = dataList;
		this.object = object;
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
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}