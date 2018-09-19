package com.cssiot.cssutil.common.dialect;

import java.sql.Types;

import org.hibernate.dialect.SQLServer2012Dialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * 
 * @description 针对SQLServer2012方言进行扩展部分数据库数据类型
 * @author song
 * @time 2015-10-25 下午11:09:29
 * 	2018-07-27 athena 修改double为float
 */
public class SQLServer2012DialectExt extends SQLServer2012Dialect {

	public SQLServer2012DialectExt() {
		super();
		//添加类型
		registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
	    registerHibernateType(Types.NCHAR, StandardBasicTypes.STRING.getName());
	    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());  
	    registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());  
	    registerHibernateType(Types.DECIMAL, StandardBasicTypes.FLOAT.getName());  
	}
}
