package com.cssiot.cssbase.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.sys.service.RoleService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 角色Controller
 * @author 
 *	2018-11-01 Diego.zhou 新建
 */
@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;
	
	/**
	 * 角色保存初始化接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@ApiOperation("角色保存/修改初始化接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="roleId",dataType="String",required=false,value="角色Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSaveRoleData/{token}/{userId}")
	public Object doSaveRoleData(@PathVariable("token") String token,@PathVariable("userId")String userId,String roleId){
		Object result = roleService.doSaveRoleData(roleId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	/**
	 * 角色保存接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@ApiOperation("角色保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=false,value="jsonStr:{}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSaveRoleInfo/{token}/{userId}")
	public Object doSaveRoleInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = roleService.doSaveRoleInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
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
	@ApiOperation("角色查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=false,value="查询条件jsonStr:{roleId:'角色Id',orderBy:'排序方式'}"),
		@ApiImplicitParam(paramType="query",name="pageNo",dataType="String",required=true,value="页码"),
		@ApiImplicitParam(paramType="query",name="pageSize",dataType="String",required=true,value="一页条数")
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSelectRoleInfo/{token}/{userId}")
	public Object doSelectRoleInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr,
			String pageSize,String pageNo,HttpServletRequest request,HttpServletResponse response){
		Object result = roleService.doSelectRoleInfo(request, response, jsonStr, pageNo, pageSize, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}	
	
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
	@ApiOperation("角色搜索下拉查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=false,value="查询条件jsonStr:{roleName:'角色名称'}"),
		@ApiImplicitParam(paramType="query",name="pageNo",dataType="String",required=true,value="页码"),
		@ApiImplicitParam(paramType="query",name="pageSize",dataType="String",required=true,value="一页条数")
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSearchRoleInfo/{token}/{userId}")
	public Object doSearchRoleInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr,
			String pageSize,String pageNo,HttpServletRequest request,HttpServletResponse response){
		Object result = roleService.doSearchRoleInfo(request, response, jsonStr, pageNo, pageSize, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	/**
	 * 获取所有角色数据接口
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@ApiOperation("获取所有角色数据接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSelectAllRoleInfo/{token}/{userId}")
	public Object doSelectAllRoleInfo(@PathVariable("token") String token,@PathVariable("userId")String userId){
		Object result = roleService.doSelectAllRoleInfo(userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	/**
	 * 角色删除接口
	 * @param roleId 角色Id
	 * @param userId 当前登录者Id
	 * @param token token令牌
	 * @return
	 * @author 
	 * 	2018-11-1 Diego.zhou 新建
	 */
	@ApiOperation("角色删除接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="roleId",dataType="String",required=false,value="roleId:角色Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doDeleteRoleInfo/{token}/{userId}")
	public Object doDeleteRoleInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String roleId){
		Object result = roleService.doDeleteRoleInfo(roleId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
}
