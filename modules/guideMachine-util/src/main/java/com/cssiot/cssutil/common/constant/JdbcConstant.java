package com.cssiot.cssutil.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库配置内容
 * @author
 *  2018-01-02 athena 新建
 */
@Configuration
public class JdbcConstant {

	//驱动类名称
	public static String driverClassName;
	
	//数据库地址
	public static String jdbcUrl;
	
	//数据库用户名
	public static String jdbcUsername;
	
	//数据库密码
	public static String jdbcPassword;

	@Value("${spring.datasource.driver-class-name}")
	public void setDriverClassName(String driverClassName) {
		JdbcConstant.driverClassName = driverClassName;
	}

	@Value("${spring.datasource.url}")
	public void setJdbcUrl(String jdbcUrl) {
		JdbcConstant.jdbcUrl = jdbcUrl;
	}

	@Value("${spring.datasource.username}")
	public void setJdbcUsername(String jdbcUsername) {
		JdbcConstant.jdbcUsername = jdbcUsername;
	}

	@Value("${spring.datasource.password}")
	public void setJdbcPassword(String jdbcPassword) {
		JdbcConstant.jdbcPassword = jdbcPassword;
	}
	
	
}