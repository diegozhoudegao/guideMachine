package com.cssiot.cssbase.modules.sys.service;

import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssutil.common.data.IsEnabledModel;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 员工表Service
 * @author
 *	2018-10-07 athena 创建
 */
public interface UserService extends BaseService<User>{
	
	/**
	 * 判断当前用户是否具有操作此功能的权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel isAccess(String userId,String content,String operate) throws Exception;
	
	/**
	 * 查询当前用户具有哪些操作权限
	 * @param userId 用户ID
	 * @param content 模块内容
	 * @param operate 功能操作
	 * @return
	 * @author
	 * 	2018-10-24 athena 复制
	 */
	@SuppressWarnings("all")
	public IsEnabledModel hasAccess(String userId,String content) throws Exception;
}