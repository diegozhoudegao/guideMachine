package com.cssiot.cssutil.common.utils;

import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

/**
 *  
 * @description 
 * @author athena
 * @time 2016-02-04
 *
 */

public class MyHibernateDaoSupport extends HibernateDaoSupport {

	@Resource(name="sessionFactory") //为父类HibernateDaoSupportע注入sessionFactory的值
	public void setSuperSessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}

}