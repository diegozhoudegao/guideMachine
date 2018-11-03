package com.cssiot.cssbase.modules.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 员工
 * @author
 * 	2018-10-09 athena 创建
 * 	2018-10-28 Diego.zhou 增加员工操作接口
 */

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation("员工模板导出接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@GetMapping("/doExportUsersTemplateInfo/{token}/{userId}")
	public void doExportUsersTemplateInfo(HttpServletResponse response,@PathVariable("token") String token,@PathVariable("userId")String userId) {
		userService.doExportUsersTemplateInfo(response);
	}
	
	@ApiOperation("员工信息导入接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
	})
	@PostMapping(value = "/doImportUsersInfo/{token}/{userId}", consumes = "multipart/*", headers = "content-type=multipart/form-data")
	public Object doImportUsersInfo(@ApiParam(value = "上传文件", required = true) MultipartFile file,@PathVariable("token") String token,@PathVariable("userId")String userId) {
		Object result = userService.doImportUsersInfo(file, token, userId);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工信息查询接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=false,value="查询条件jsonStr:{province:'省份',city:'城市',"
				+ "county:'区县',scenicSpotId:'注册景点Id',"
				+ "phone:'手机',roleId:'角色Id',loginName:'账号',userName:'姓名',"
				+ "orderBy:'排序方式'}"),
		@ApiImplicitParam(paramType="query",name="pageNo",dataType="String",required=true,value="页码"),
		@ApiImplicitParam(paramType="query",name="pageSize",dataType="String",required=true,value="一页条数")
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSelectUsersInfo/{token}/{userId}")
	public Object doSelectUsersInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr,
			String pageSize,String pageNo,HttpServletRequest request,HttpServletResponse response){
		Object result = userService.doSelectUsersInfo(request, response, jsonStr, pageNo, pageSize, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工信息新建保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="保存jsonStr:{userModel:{userName:'姓名',roleId:'角色Id',"
				+ "loginName:'账号',phone:'手机',password:'密码',manageList:[{province:'省份',city:'城市',county:'区县',scenicSpotId:'景点Id'}]}}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doSaveUsersInfo/{token}/{userId}")
	public Object doSaveUsersInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = userService.doSaveUsersInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工信息修改初始化接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="usersId",dataType="String",required=true,value="员工Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateUsersData/{token}/{userId}")
	public Object doUpdateUsersData(@PathVariable("token") String token,@PathVariable("userId")String userId,String usersId){
		Object result = userService.doUpdateUsersData(usersId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工信息修改保存接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="jsonStr",dataType="String",required=true,value="保存jsonStr:{userModel:{userId:'员工Id',userName:'姓名',roleId:'角色Id',"
				+ "loginName:'账号',phone:'手机',password:'密码',manageList:[{province:'省份',city:'城市',county:'区县',scenicSpotId:'景点Id'}]}}"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doUpdateUsersInfo/{token}/{userId}")
	public Object doUpdateUsersInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String jsonStr){
		Object result = userService.doUpdateUsersInfo(jsonStr, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工离职接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="usersId",dataType="String",required=true,value="员工Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doDisableUsersInfo/{token}/{userId}")
	public Object doDisableUsersInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String usersId){
		Object result = userService.doDisableUsersInfo(usersId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
	@ApiOperation("员工离职恢复接口")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="path",name="token",dataType="String",required=true,value="安全令牌"),
		@ApiImplicitParam(paramType="path",name="userId",dataType="String",required=true,value="当前登录者Id"),
		@ApiImplicitParam(paramType="query",name="usersId",dataType="String",required=true,value="员工Id"),
	})
	@SuppressWarnings("all")
	@PostMapping(value="/doEnableUsersInfo/{token}/{userId}")
	public Object doEnableUsersInfo(@PathVariable("token") String token,@PathVariable("userId")String userId,String usersId){
		Object result = userService.doEnableUsersInfo(usersId, userId, token);
		return ResultUtil.success(result, ResultEnum.SUCCESS, token, userId, null);
	}
	
}