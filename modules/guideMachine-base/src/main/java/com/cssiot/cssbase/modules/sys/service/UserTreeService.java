package com.cssiot.cssbase.modules.sys.service;

import com.cssiot.cssbase.modules.sys.entity.UserTree;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 员工树视图Service
 * @author
 *	2018-10-07 athena 创建
 */
public interface UserTreeService extends BaseService<UserTree>{
	
	/**
	 * 获取用户对应树菜单接口
	 * @param loginName 用户名
	 * @param token 安全令牌
	 * @param clientType 客户端类型(android/ios/web等)
	 * @return treeText 树结构
	 * @author
	 *	2018-10-09 athena 迁移
	 */
	@SuppressWarnings("all")
	public Object getTreeText(String loginName,String token,String clientType);
}