package com.cssiot.cssbase.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssutil.common.enums.ResultEnum;
import com.cssiot.cssutil.common.utils.ResultUtil;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {

	public static void main(String[] args) {
		
	}
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value="用户保存", notes="用户保存")
	@PostMapping("/saveUser")
	public Object doSaveUser() {
		Object result = userService.doSaveUser();
		return ResultUtil.success(result, ResultEnum.SUCCESS, null, null, null);
	}
	
	@ApiOperation(value="用户获取", notes="用户获取")
	@PostMapping("/doGetUserByName")
	public Object doGetUserByName() {
		Object result = userService.doGetUserByName("");
		return ResultUtil.success(result, ResultEnum.SUCCESS, null, null, null);
	}
}
