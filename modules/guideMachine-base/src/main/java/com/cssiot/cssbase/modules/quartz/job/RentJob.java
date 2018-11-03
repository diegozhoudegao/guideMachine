package com.cssiot.cssbase.modules.quartz.job;

import java.time.LocalDate;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssbase.modules.quartz.data.RentModel;
import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * 租借订单定时任务
 * @ClassName: ReturnJob
 * @Description: 
 * @author 
 * 	2018-10-31 Diego.zhou 新建
 */
@Slf4j
@Component
@DisallowConcurrentExecution
@Transactional
public class RentJob extends QuartzJobBean {
	
	private static final String RENT_GROUP_NAME = "RENT";
	
	@Autowired
	private  JobService jobService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("RentJob定时任务开始执行：{}",LocalDate.now().toString());
		JobDataMap dataMap = context.getMergedJobDataMap();
		String messageInfo = dataMap.getString("rentModel");
		Gson gson = new Gson();
		RentModel model = gson.fromJson(messageInfo, RentModel.class);
		log.info("获得的数据为：{}",model.toString());
		boolean deleteJobIsSuccess = jobService.deleteJob(model.getTransactionNo(), RENT_GROUP_NAME);
		if (!deleteJobIsSuccess) {
			log.error("发送一次移除定时任务失败:={},{}", model.getTransactionNo(),RENT_GROUP_NAME);
		}
	}
}
