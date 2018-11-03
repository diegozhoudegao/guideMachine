package com.cssiot.cssbase.modules.quartz.service.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssbase.modules.quartz.data.RentModel;
import com.cssiot.cssbase.modules.quartz.data.ReturnModel;
import com.cssiot.cssbase.modules.quartz.job.RentJob;
import com.cssiot.cssbase.modules.quartz.job.ReturnJob;
import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.cssiot.cssbase.modules.quartz.service.ScheduleCronJobService;
import com.cssiot.cssutil.common.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 动态创建定时任务Service
 * @author 
 *	2018-10-31 Diego.zhou 新建
 */
@Service
@Transactional
@Slf4j
public class ScheduleCronJobServiceImpl implements ScheduleCronJobService {
	
	private static final String RENT_GROUP_NAME = "RENT";
	
	private static final String RETURN_GROUP_NAME = "RETURN";
	
	@Autowired
	private JobService jobService;
	
	/**
	 * 订单租借时新增定时任务
	 * @param rentModel
	 */
	public void doScheduleRentJob(RentModel rentModel) {
		//获取当前时间
    	LocalDateTime now = LocalDateTime.now();
    	//获取当前时间10s后时间
    	LocalDateTime afterTime = now.plus(10, ChronoUnit.SECONDS);
    	//获取afterTime的详细信息
    	String cronExpression= String.format("%s %s %s %s %s %s %s", 
    			afterTime.getSecond(), afterTime.getMinute(), afterTime.getHour(), afterTime.getDayOfMonth(), afterTime.getMonthValue(), "?",afterTime.getYear());
		//生成定时任务
    	Map params = new HashMap<>();
		params.put("rentModel", ResponseUtils.toJSONString(rentModel));
		if (jobService.isJobWithNamePresent(rentModel.getTransactionNo(), RENT_GROUP_NAME)) {
			jobService.updateCronJob(rentModel.getTransactionNo(), RENT_GROUP_NAME, cronExpression);
		}else {
			jobService.scheduleCronJob(rentModel.getTransactionNo(), RENT_GROUP_NAME, RentJob.class, cronExpression,params, true);
		}
	}
	
	/**
	 * 扫码归还时新增定时任务
	 * @param returnModel
	 */
	public void doScheduleReturnJob(ReturnModel returnModel) {
		//获取当前时间
    	LocalDateTime now = LocalDateTime.now();
    	//获取当前时间10s后时间
    	LocalDateTime afterTime = now.plus(10, ChronoUnit.SECONDS);
    	//获取afterTime的详细信息
    	String cronExpression= String.format("%s %s %s %s %s %s %s", 
    			afterTime.getSecond(), afterTime.getMinute(), afterTime.getHour(), afterTime.getDayOfMonth(), afterTime.getMonthValue(), "?",afterTime.getYear());
		//生成定时任务
    	Map params = new HashMap<>();
		params.put("returnModel", ResponseUtils.toJSONString(returnModel));
		if (jobService.isJobWithNamePresent(returnModel.getTransactionNo(), RETURN_GROUP_NAME)) {
			jobService.updateCronJob(returnModel.getTransactionNo(), RETURN_GROUP_NAME, cronExpression);
		}else {
			jobService.scheduleCronJob(returnModel.getTransactionNo(), RETURN_GROUP_NAME, ReturnJob.class, cronExpression,params, true);
		}
	}
}
