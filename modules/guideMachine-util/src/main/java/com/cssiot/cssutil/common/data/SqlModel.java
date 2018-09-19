package com.cssiot.cssutil.common.data;

import lombok.Data;

/**
 * sqlModel，用于生成sql语句
 * @author 
 * 	2018-01-02 Diego.zhou 新建
 */
@Data
public class SqlModel {
	private String name;		//字段名称
	private String sqlType;		//字段类型
	private String length;		//字段长度
}
