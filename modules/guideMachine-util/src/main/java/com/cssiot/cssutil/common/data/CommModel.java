package com.cssiot.cssutil.common.data;

import java.util.List;

import com.cssiot.cssutil.common.utils.ChkUtil;
/**
 * 共同模型字段
 * @author
 *	2017-10-09 athena 创建
 */
public class CommModel {
	private String id;		//id
	private String name;	//名称
	private String type;	//类型
	private String flag;	//miaoLiusuo 2017/2/8
	private List list;		//miaoLiusuo 2017/2/8
	public CommModel() {
		super();
	}
	public CommModel(String id, String name, String type, String flag, List list) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.flag = flag;
		this.list = list;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = ChkUtil.isEmpty(id)?"":id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = ChkUtil.isEmpty(name)?"":name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	
	@Override  
	public boolean equals(Object obj) {  
		CommModel s=(CommModel)obj;
		String ids=ChkUtil.isEmpty(s.getId())?"":s.getId();
		String names=ChkUtil.isEmpty(s.getName())?"":s.getName();
		return id.equals(ids) && name.equals(names);   
	}
	
	@Override  
	public int hashCode() {  
		String in = id+name;  
		return in.hashCode();  
	}
	
}
