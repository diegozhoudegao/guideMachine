package com.cssiot.cssweb.modules.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试并发(100)情况下，通过redis计数器实现自增流水号
 * @author 
 * 	2018-09-26 Diego.zhou 新建
 */
@RestController
public class RedisController {
	

	@Autowired
	private StringRedisTemplate redis;

	private static final String SERIALNUMBER = "SERIALNUMBER";
	private static final String ZDG = ":ZDG:";
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
	
	/**
	 * 测试接口
	 * @return
	 * 	并发数100的情况下生成的流水号，判断是否存在重复以及断号情况
	 */
	@GetMapping("/testRedisIncr")
	public String doTestRedisIncr() {
		//获取SERIALNUMBER:ZDG:时间,对应的value值，并设置对应的过期时间为60s
		//获取当前时间
		String time = sdf.format(new Date());
		String key = SERIALNUMBER+ZDG+time;
		//获取key对应的value，并默认+1
		long result = redis.opsForValue().increment(key, 1);//原子操作
		if(result == 1) {//第一次时设置过期时间为60s
			redis.expire(key, 60, TimeUnit.SECONDS);
		}
		if(result>=1 && result<10) {
			return key+"00"+result;//SERIALNUMBER:ZDG:001
		}else if(result>=10 && result<100) {
			return key+"0"+result;//SERIALNUMBER:ZDG:011
		}else {
			return key+result;//SERIALNUMBER:ZDG:100
		}
	}
}
