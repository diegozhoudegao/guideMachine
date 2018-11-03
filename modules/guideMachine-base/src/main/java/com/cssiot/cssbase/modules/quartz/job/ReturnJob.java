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

import com.cssiot.cssbase.modules.quartz.data.ReturnModel;
import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

/**
 * 导游机归还定时任务
 * @ClassName: ReturnJob
 * @Description: 
 * @author 
 * 	2018-10-31 Diego.zhou 新建
 */
@Slf4j
@Component
@DisallowConcurrentExecution
@Transactional
public class ReturnJob extends QuartzJobBean {
	
	private static final String RETURN_GROUP_NAME = "RETURN";
	
	@Autowired
	private  JobService jobService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		log.info("ReturnJob定时任务开始执行：{}",LocalDate.now().toString());
		JobDataMap dataMap = context.getMergedJobDataMap();
		String messageInfo = dataMap.getString("returnModel");
		Gson gson = new Gson();
		ReturnModel model = gson.fromJson(messageInfo, ReturnModel.class);
		log.info("获得的数据为：{}",model.toString());
		boolean deleteJobIsSuccess = jobService.deleteJob(model.getTransactionNo(), RETURN_GROUP_NAME);
		if (!deleteJobIsSuccess) {
			log.error("发送一次移除定时任务失败:={},{}", model.getTransactionNo(),RETURN_GROUP_NAME);
		}
	}
}
