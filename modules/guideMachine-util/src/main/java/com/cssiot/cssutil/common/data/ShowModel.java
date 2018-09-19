package com.cssiot.cssutil.common.data;

import com.cssiot.cssutil.common.utils.ChkUtil;

/**
 * 
 * @description 公用方法
 * @author athena
 * @time 2017-07-02
 *	2018-03-22 Diego.zhou 增加是否用户字段，自定义表单用
 *	2018-05-30 Diego.zhou 增加组件类型字段
 */

public class ShowModel {
	private String id;			//id
	private String showName;	//名称
	private String isUser;		//是否用户（0是1否）
	private String formId;		//表单Id
	private String type;		//组件类型
	private String defaultType;	//默认值类型
	private String isChildElement;//是否子表单组件
	public ShowModel() {
		super();
	}
	
	public ShowModel(String id, String showName) {
		super();
		this.id = id;
		this.showName = showName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getIsUser() {
		return isUser;
	}

	public void setIsUser(String isUser) {
		this.isUser = isUser;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(String defaultType) {
		this.defaultType = defaultType;
	}

	public String getIsChildElement() {
		return isChildElement;
	}

	public void setIsChildElement(String isChildElement) {
		this.isChildElement = isChildElement;
	}

	@Override  
	public boolean equals(Object obj) {  
		ShowModel s=(ShowModel)obj;
		String ids=ChkUtil.isEmpty(s.getId())?"":s.getId();
		String names=ChkUtil.isEmpty(s.getShowName())?"":s.getShowName();
		return id.equals(ids) && showName.equals(names);   
	}
	
	@Override  
	public int hashCode() {  
		String in = id+showName;  
		return in.hashCode();  
	}
	
}