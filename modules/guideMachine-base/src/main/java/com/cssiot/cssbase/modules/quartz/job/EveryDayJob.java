package com.cssiot.cssbase.modules.quartz.job;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import lombok.extern.slf4j.Slf4j;

/**
 * 每天定时执行job
 * @author 
 * 	2018-10-16 Diego.zhou 新建
 *
 */
@Slf4j
@DisallowConcurrentExecution
public class EveryDayJob implements Job {

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("每天定点执行的定时任务job,本次执行时间为{}:",sdf.format(new Date()));
		//TODO 补充业务逻辑
	}

}
