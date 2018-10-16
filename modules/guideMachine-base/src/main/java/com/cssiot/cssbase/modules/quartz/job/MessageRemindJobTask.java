package com.cssiot.cssbase.modules.quartz.job;

import java.time.LocalDate;
import java.time.LocalTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssbase.modules.quartz.data.MessageRemindModel;
import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.cssiot.cssutil.common.enums.MessageRemindSendTypeEnum;
import com.cssiot.cssutil.common.utils.Encodes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.extern.slf4j.Slf4j;

/**
 * 发送消息的定时任务
 * @ClassName: MessageRemindJobTask
 * @Description: 
 * @author 
 * 2018年9月27日  tck 创建
 */
@Slf4j
@Component
@DisallowConcurrentExecution
@Transactional
public class MessageRemindJobTask extends QuartzJobBean {
	
	
	@Autowired
	private  JobService jobService;
	
	
	@Override
	public void executeInternal(JobExecutionContext context) throws JobExecutionException{
		log.info("发送消息开始:={}",LocalDate.now().toString() + " " + LocalTime.now());
		JobDataMap dataMap = context.getMergedJobDataMap();
		String modelStr = dataMap.get("messageRemindModel").toString();
		Gson gson = new Gson();
		MessageRemindModel model = gson.fromJson(Encodes.unescapeHtml(modelStr),new TypeToken<MessageRemindModel>(){}.getType());
		// 若是定时发送一次,则移除定时任务
		if (MessageRemindSendTypeEnum.ONCE.getCode().equals(model.getSendType())) {
			boolean deleteJobIsSuccess = jobService.deleteJob(model.getUniqueName(), model.getCustomerId());
			if (!deleteJobIsSuccess) {
				log.error("发送一次移除定时任务失败:={},{}", LocalDate.now().toString() + " " + LocalTime.now(), model.toString());
			}
		}
	}
}
