package com.cssiot.cssutil.common.utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 初始化servlet，在web.xml中引用
 * 启动tomcat时即初始化完成，放token缓存用
 * @author cheng.zhang
 *2016-02-16
 */
public class InitServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static WebApplicationContext wc;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		//初始化spring的工厂
		wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
		WebApplicationContext cont = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext()); 
		String ctx=this.getServletContext().getContextPath();
		this.getServletContext().setAttribute("ctx", ctx);
	}
	
	public static WebApplicationContext getWc() {
		return wc;
	}
}
