package com.cssiot.cssbase.modules.quartz.service;

import java.util.Map;

import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 定时任务工具
 * @ClassName: JobService
 * @Description: 
 * @author 
 * 2018年9月27日  tck 创建
 */
public interface JobService {
	
	/**
	 * 新增cron定时任务
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param jobClass 定时任务类
	 * @param cron 表达式
	 * @param params 参数
	 * @param isDurable 是否持久化到数据库
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean scheduleCronJob(String jobName, String jobGroupName, 
		       Class<? extends QuartzJobBean> jobClass, 
		       String cron, Map<String,Object> params, boolean isDurable);
	
	/**
	 * 修改定时任务的cron表达式(但是修改不了该定时任务的JobDataMap)
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param cronExpression 表达式
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean updateCronJob(String jobName, String jobGroupName, String cronExpression);
	
	/**
	 * 删除任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean deleteJob(String jobName, String jobGroupName);
	
	/**
	 * 暂停任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean pauseJob(String jobName, String jobGroupName);
	
	/**
	 * 继续任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean resumeJob(String jobName, String jobGroupName);
	
	/**
	 * 开始任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean startJobNow(String jobName, String jobGroupName);
	
	/**
	 * 检查任务是否在运行中
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean isJobRunning(String jobName, String jobGroupName);
	
	/**
	 * 获取所有任务
	 * @param jobName 组名,客户id
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
//	List<Map<String, Object>> getAllJobs();
	Object getAllJobs(String quaryGroupName);
	
	/**
	 * 指定名字的任务是否存在
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean isJobWithNamePresent(String jobName, String jobGroupName);
	
	/**
	 * 获取指定任务的状态
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	String getJobState(String jobName, String jobGroupName);
	
	/**
	 * 中断任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	boolean interruptJob(String jobName, String jobGroupName);
}