package com.cssiot.cssbase.modules.sys.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssutil.common.data.IsEnabledModel;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 用户Service
 */
public interface UserService extends BaseService<User> {
	
	public Object doSaveUser() ;
	
	public Object doGetUserByName(String userName) ;
}