package com.cssiot.cssutil.common.dialect;

import java.sql.Types;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * 
 * @description 针对MySQL5方言进行扩展部分数据库数据类型
 * @author athena
 * @time 2016-08-16
 */
public class Mysql5DialectExt extends MySQL5Dialect {

	public Mysql5DialectExt() {
		super();
		//添加类型
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
	    registerHibernateType(Types.NCHAR, StandardBasicTypes.STRING.getName());
	    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());  
	    registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());  
	    registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());  
	    registerHibernateType(Types.BIGINT, StandardBasicTypes.INTEGER.getName());  
	}
}
