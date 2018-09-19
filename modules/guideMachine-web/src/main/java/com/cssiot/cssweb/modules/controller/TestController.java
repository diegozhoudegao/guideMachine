package com.cssiot.cssweb.modules.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class TestController {

	@ApiOperation(value="test", notes="测试接口")
	@GetMapping("/test")
	public String test() {
		return "hello";
	}
}
