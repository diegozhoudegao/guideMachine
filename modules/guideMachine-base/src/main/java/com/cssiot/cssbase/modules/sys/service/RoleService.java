package com.cssiot.cssbase.modules.sys.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cssiot.cssbase.modules.sys.entity.Role;
import com.cssiot.cssutil.common.service.BaseService;

/**
 * 角色表Service
 * @author
 *	2018-10-07 athena 创建
 */
public interface RoleService extends BaseService<Role>{
	
	/**
	 * 角色保存初始化接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	public Object doSaveRoleData(String roleId,String userId,String token);
	
	/**
	 * 角色保存接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	public Object doSaveRoleInfo(String jsonStr,String userId,String token);
	
	/**
	 * 角色查询接口
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @param pageNo
	 * @param pageSize
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	public Object doSelectRoleInfo(HttpServletRequest request, HttpServletResponse response,String jsonStr,String pageNo,String pageSize,String userId,String token);
	
	/**
	 * 角色搜索下拉查询接口
	 * @param request
	 * @param response
	 * @param jsonStr
	 * @param pageNo 
	 * @param pageSize
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 */
	public Object doSearchRoleInfo(HttpServletRequest request, HttpServletResponse response,String jsonStr,String pageNo,String pageSize,String userId,String token);
	
	/**
	 * 获取所有角色数据接口
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	public Object doSelectAllRoleInfo(String userId,String token);
	
	/**
	 * 角色删除接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	public Object doDeleteRoleInfo(String roleId,String userId,String token);
}