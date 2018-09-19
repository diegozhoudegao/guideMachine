package com.cssiot.cssutil.common.data;

import lombok.Data;

/**
 * 通用树结构的模型类 
 * @ClassName: CommonTreeModel
 * @Description: 
 * @author 
 * 2018年1月10日  tck 创建
 */
@Data
public class CommonTreeModel {
	private String id;          //编码
	private String pId;         //上级编码
	private String name;        //名称
	private String entityId;	//实体id
	private String checked;		//是否被勾选
}
