package com.cssiot.cssweb.modules.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.websocket.WebSocketServer;

import io.swagger.annotations.ApiOperation;

@RestController
public class TestController {

	@ApiOperation(value="test", notes="测试接口")
	@GetMapping("/test")
	public String test() {
		return "hello";
	}
	
	@ApiOperation(value="testMsg", notes="测试websocket给指定用户发送消息接口")
	@GetMapping("/testMsg")
	public void testSendMsg() throws IOException {
		WebSocketServer.sendMessageTo("给用户1发送一条消息，哈哈哈，欢迎1", "1");
		WebSocketServer.sendMessageTo("给用户1发送一条消息，哈哈哈，欢迎2", "2");
	}
}
