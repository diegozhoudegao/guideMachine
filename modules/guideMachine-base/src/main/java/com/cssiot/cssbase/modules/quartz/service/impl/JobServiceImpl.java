package com.cssiot.cssbase.modules.quartz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.cssiot.cssbase.modules.quartz.utils.JobUtil;
import com.cssiot.cssutil.common.utils.ChkUtil;

/**
 * 定时任务工具
 * @ClassName: JobService
 * @Description: 
 * @author 
 * 2018年9月27日  tck 创建
 */
@Service
@Transactional
public class JobServiceImpl implements JobService{

	@Autowired
	@Lazy
	SchedulerFactoryBean schedulerFactoryBean;

	@Autowired
	private ApplicationContext context;
	
	/**
	 * 新增cron定时任务
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param jobClass 定时任务类
	 * @param cronExpression 表达式
	 * @param params 参数
	 * @param isDurable 是否持久化到数据库
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean scheduleCronJob(String jobName, String jobGroupName, 
							       Class<? extends QuartzJobBean> jobClass, 
							       String cronExpression, Map<String,Object> params, boolean isDurable) {
		JobDetail jobDetail = JobUtil.createJob(jobName, jobGroupName, jobClass, params, isDurable, context);
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		// 触发器  
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		// 触发器名,触发器组  
		triggerBuilder.withIdentity(jobName, jobGroupName);
		triggerBuilder.startNow();
		// 触发器时间设定  
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
		// 创建Trigger对象
		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
		// 调度容器设置JobDetail和Trigger
		try {
			scheduler.scheduleJob(jobDetail, trigger);
			// 启动  
			if (!scheduler.isShutdown()) {  
				scheduler.start();
				return true;
			} else {
				return false;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}  
	}

	/**
	 * 修改定时任务的cron表达式(但是修改不了该定时任务的JobDataMap)
	 * @param jobName 任务名
	 * @param jobGroupName 任务组名
	 * @param cronExpression 表达式
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean updateCronJob(String jobName, String jobGroupName, String cronExpression) {
		// 触发器  
		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
		// 触发器名,触发器组  
		triggerBuilder.withIdentity(jobName, jobGroupName);
		triggerBuilder.startNow();
		// 触发器时间设定  
		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
		// 创建Trigger对象
		CronTrigger newTrigger = (CronTrigger) triggerBuilder.build();
		try {
			schedulerFactoryBean.getScheduler().rescheduleJob(TriggerKey.triggerKey(jobName, jobGroupName), newTrigger);
			return true;
		} catch ( Exception e ) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 删除任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean deleteJob(String jobName, String jobGroupName) {
		Scheduler sched = schedulerFactoryBean.getScheduler();  
		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
		try {
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey); 
		    if( null==trigger ) {
		    	return false;
		    }
			sched.pauseTrigger(triggerKey);// 停止触发器  
			sched.unscheduleJob(triggerKey);// 移除触发器 
			JobKey jkey = new JobKey(jobName, jobGroupName); 
			return sched.deleteJob(jkey);
		} catch (Exception e) {  
			e.printStackTrace();
			return false;
    	} 
	}

	/**
	 * 暂停任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean pauseJob(String jobName, String jobGroupName) {
		JobKey jkey = new JobKey(jobName, jobGroupName); 
		try {
			schedulerFactoryBean.getScheduler().pauseJob(jkey);
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 继续任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean resumeJob(String jobName, String jobGroupName) {
		JobKey jKey = new JobKey(jobName, jobGroupName); 
		try {
			schedulerFactoryBean.getScheduler().resumeJob(jKey);
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 开始任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean startJobNow(String jobName, String jobGroupName) {
		JobKey jKey = new JobKey(jobName, jobGroupName); 
		try {
			schedulerFactoryBean.getScheduler().triggerJob(jKey);
			return true;
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}		
	}

	/**
	 * 检查任务是否在运行中
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean isJobRunning(String jobName, String jobGroupName) {
		try {
			List<JobExecutionContext> currentJobs = schedulerFactoryBean.getScheduler().getCurrentlyExecutingJobs();
			if(currentJobs!=null){
				for (JobExecutionContext jobCtx : currentJobs) {
					String jobNameDB = jobCtx.getJobDetail().getKey().getName();
					String groupNameDB = jobCtx.getJobDetail().getKey().getGroup();
					if (jobName.equalsIgnoreCase(jobNameDB) && jobGroupName.equalsIgnoreCase(groupNameDB)) {
						return true;
					}
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * 获取所有任务
	 * @param jobName 组名,客户id
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object getAllJobs(String quaryGroupName) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			List<String> allGroupName = new ArrayList<>();
			if (!ChkUtil.isEmptyAllObject(quaryGroupName)) {
				allGroupName.add(quaryGroupName);
			} else {
				allGroupName = scheduler.getJobGroupNames();
			}
			
			for (String groupName : allGroupName) {
				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					String jobName = jobKey.getName();
					String jobGroup = jobKey.getGroup();

					//get job's trigger
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
					Date scheduleTime = triggers.get(0).getStartTime();
					Date nextFireTime = triggers.get(0).getNextFireTime();
					Date lastFiredTime = triggers.get(0).getPreviousFireTime();
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("jobName", jobName);
					map.put("groupName", jobGroup);
					map.put("scheduleTime", scheduleTime);
					map.put("lastFiredTime", lastFiredTime);
					map.put("nextFireTime", nextFireTime);
					
					if(isJobRunning(jobName, jobGroup)){
						map.put("jobStatus", "RUNNING");
					}else{
						String jobState = getJobState(jobName, jobGroup);
						map.put("jobStatus", jobState);
					}

					/*					Date currentDate = new Date();
					if (scheduleTime.compareTo(currentDate) > 0) {
						map.put("jobStatus", "scheduled");

					} else if (scheduleTime.compareTo(currentDate) < 0) {
						map.put("jobStatus", "Running");

					} else if (scheduleTime.compareTo(currentDate) == 0) {
						map.put("jobStatus", "Running");
					}*/

					list.add(map);
				}

			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		Map<String,Object> map = new HashMap<>();
		map.put("allJob", list);
		return map;
	}

	/**
	 * 指定名字的任务是否存在
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean isJobWithNamePresent(String jobName, String jobGroupName) {
		try {
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			if (scheduler.checkExists(jobKey)){
				return true;
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 获取指定任务的状态
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	public String getJobState(String jobName, String jobGroupName) {
		try {
			JobKey jobKey = new JobKey(jobName, jobGroupName);

			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobDetail jobDetail = scheduler.getJobDetail(jobKey);

			List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobDetail.getKey());
			if(triggers != null && triggers.size() > 0){
				for (Trigger trigger : triggers) {
					TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());

					if (TriggerState.PAUSED.equals(triggerState)) {
						return "PAUSED";
					}else if (TriggerState.BLOCKED.equals(triggerState)) {
						return "BLOCKED";
					}else if (TriggerState.COMPLETE.equals(triggerState)) {
						return "COMPLETE";
					}else if (TriggerState.ERROR.equals(triggerState)) {
						return "ERROR";
					}else if (TriggerState.NONE.equals(triggerState)) {
						return "NONE";
					}else if (TriggerState.NORMAL.equals(triggerState)) {
						return "SCHEDULED";
					}
				}
			}
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 中断任务
	 * @param jobName
	 * @param jobGroupName
	 * @return
	 * @author
	 * 2018年9月27日  tck 创建
	 */
	@Override
	public boolean interruptJob(String jobName, String jobGroupName) {
		try{	
			Scheduler scheduler = schedulerFactoryBean.getScheduler();
			JobKey jkey = new JobKey(jobName, jobGroupName);
			return scheduler.interrupt(jkey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return false;
	}
}

