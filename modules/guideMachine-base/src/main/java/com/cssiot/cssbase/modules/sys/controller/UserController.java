package com.cssiot.cssbase.modules.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cssiot.cssbase.modules.sys.service.UserService;

/**
 * 员工
 * @author
 * 	2018-10-09 athena 创建
 */

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
}