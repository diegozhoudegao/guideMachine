package com.cssiot.cssbase.modules.quartz.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cssiot.cssbase.modules.quartz.data.MessageRemindModel;
import com.cssiot.cssbase.modules.quartz.service.SendMessageRemindService;
import com.cssiot.cssutil.common.enums.MessageRemindSendTypeEnum;

/**
 * 测试定时
 * @author 
	2018-10-16 Diego.zhou 新建
 *
 */
@RestController
@RequestMapping("/quartz")
public class QuartzTestController {

	@Autowired
	private SendMessageRemindService sendMessageRemindService;
	
	@GetMapping("/testQuartz/{time}")
	public String testQuartz(@PathVariable String time) {
		MessageRemindModel jsonModel = new MessageRemindModel();
		jsonModel.setSendType(MessageRemindSendTypeEnum.ONCE.getCode());//重复执行
		jsonModel.setRemindTime(time);
		jsonModel.setMessageRemindId("MessageRemindId");
		jsonModel.setCustomerId("CustomerId");
		sendMessageRemindService.doSendMeassageRemind(jsonModel);
		return "执行定时任务";
	}
	
	public static void main(String[] args) throws ParseException {
		long nextFireTime = Long.valueOf("1539702899000");
		Long startTime = Long.valueOf("1539702789000");
		Date date = new Date(nextFireTime);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("nextFireTime:"+sdf.format(date));
		date = new Date(startTime);
		System.out.println("startTime:"+sdf.format(date));
		//生成自定义nextFireTime
		String fireTime = "2018-10-16 23:30:00";
		long fireTimeLong = sdf.parse(fireTime).getTime();
		long startTimeLong = fireTimeLong-10000;
		System.out.println("fireTimeLong:"+fireTimeLong);
		System.out.println("startTimeLong:"+startTimeLong);
	}
}
