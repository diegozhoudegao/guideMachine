package com.cssiot.cssutil.common.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CalendarIntervalTrigger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
 
/**
 * 处理定时任务调度队列
* @ClassName: QuartzFactoryUtil 
* @Description: TODO （描述）
* @author
* 	2017-11-11 shinry 创建重构
 */
public class QuartzFactoryUtil {
	
	private static Map<String,String> AllJobs=new HashMap<String, String>();
	
	private static QuartzFactoryUtil quartzFactoryUtil=new QuartzFactoryUtil();
   
	/**
	 * 默认cron 当前0分钟开始，间隔10分钟
	 */
	public static String DEFAULT_CRON_EXPRESSION = "0 0/10 * * * ?";  
	/**
	 * 任务名称
	 */
    public static String JOB_NAME = "GZZ02";  
    /**
	 * 触发器名称
	 */
    public static String TRIGGER_NAME = "GZZ02"; 
    /**
	 * 任务名称组别
	 */
    public static String JOB_GROUP_NAME = "GZZ02_JOB_GROUP";  
    /**
	 * 触发器名称组别
	 */
    public static String TRIGGER_GROUP_NAME = "GZZ02_TRIGGER_GROUP"; 
    
    private  static SchedulerFactory schedulerFactory = new StdSchedulerFactory();  
    private QuartzFactoryUtil(){}
    public synchronized static QuartzFactoryUtil getInstance(){
        if(quartzFactoryUtil == null){
        	quartzFactoryUtil= new QuartzFactoryUtil();
        }
        return quartzFactoryUtil;
    }
    
    /** 
     * @Description: 添加一个定时任务 
     * @param jobName 任务名 
     * @param jobGroupName  任务组名 
     * @param triggerName 触发器名 
     * @param triggerGroupName 触发器组名 
     * @param jobClass  任务 
     * @param cron   时间设置，参考quartz说明文档  
     */  
    public  void addJob(String jobName, String jobGroupName, 
            String triggerName, String triggerGroupName, Class<? extends Job> jobClass, String cron,Map<String,Object> params) {  
        try {  
        	
        	if(findModifyJobExist(jobName, jobGroupName, triggerName, triggerGroupName, cron)){
    			return;
    		}
        	
        	AllJobs.put(jobName, jobName);
            Scheduler sched = schedulerFactory.getScheduler();  
            // 任务名，任务组，任务执行类
            JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
            
            if(null!=params&&params.size()>0){
            	JobDataMap dataMap=jobDetail.getJobDataMap();
            	 for(Entry<String, Object> entry:params.entrySet()){
        			 String key=entry.getKey();
        			 Object value=entry.getValue();
        			 dataMap.put(key, value);
        		 }
            }
            // 触发器  
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组  
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startNow();
            // 触发器时间设定  
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
            // 创建Trigger对象
            CronTrigger trigger = (CronTrigger) triggerBuilder.build();

            // 调度容器设置JobDetail和Trigger
            sched.scheduleJob(jobDetail, trigger);  
            // 启动  
            if (!sched.isShutdown()) {  
                sched.start();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
    /**
     * 此方法可能不准，由于 默认会立即执行
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param cron
     * @param params
     */
    public  void addJobByCron(String jobName, String jobGroupName, Class<? extends Job> jobClass, String cron,Map<String,Object> params) {  
    	try {  
    		if(findModifyJobExistByCron(jobName, jobGroupName, cron)){
    			return;
    		}
    		AllJobs.put(jobName, jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		// 任务名，任务组，任务执行类
    		JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    		   if(null!=params&&params.size()>0){
               	JobDataMap dataMap=jobDetail.getJobDataMap();
               	 for(Entry<String, Object> entry:params.entrySet()){
           			 String key=entry.getKey();
           			 Object value=entry.getValue();
           			 dataMap.put(key, value);
           		 }
               }
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
    		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    		// 创建Trigger对象
    		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
    		
    		// 调度容器设置JobDetail和Trigger
    		sched.scheduleJob(jobDetail, trigger);  
    		// 启动  
    		if (!sched.isShutdown()) {  
    			sched.start();  
    		}  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    }
    
    /**
     * 根据cron定时器表达式 延迟秒数执行，动态拼接秒和分钟，如0 1/10 * * * ?
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param cron 定时器表达式
     * @param params
     * @param delayedTime  延迟秒数 （不会立即执行）
     */
    public  void addJobByCron(String jobName, String jobGroupName, Class<? extends Job> jobClass, String cron,Map<String,Object> params,int delayedTime) {  
    	try {  
    		if(findModifyJobExistByCron(jobName, jobGroupName, cron,delayedTime)){
    			return;
    		}
    		AllJobs.put(jobName, jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		// 任务名，任务组，任务执行类
    		JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    		   if(null!=params&&params.size()>0){
               	JobDataMap dataMap=jobDetail.getJobDataMap();
               	 for(Entry<String, Object> entry:params.entrySet()){
           			 String key=entry.getKey();
           			 Object value=entry.getValue();
           			 dataMap.put(key, value);
           		 }
               }
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
//    		triggerBuilder.startNow();
    		Date triggerStartTime=new Date(System.currentTimeMillis()+delayedTime);
			triggerBuilder.startAt(triggerStartTime);
    		// 触发器时间设定  
    		triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    		// 创建Trigger对象
    		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
    		
    		// 调度容器设置JobDetail和Trigger
    		sched.scheduleJob(jobDetail, trigger);  
    		// 启动  
    		if (!sched.isShutdown()) {  
    			sched.start();  
//    			sched.startDelayed(delayedTime);
    		}  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    }  
    
    /**
     * 按当前时间， 间隔分钟进行执行
     * intervalMinutes 须>0
     * @Description:  
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param intervalMinutes 间隔分钟>0
     * @param params
     */
    public  void addJobByCalendar(String jobName, String jobGroupName, Class<? extends Job> jobClass, int  intervalMinutes,Map<String,Object> params) {  
    	try {  
    		if(findModifyJobExistByCalendar(jobName, jobGroupName, intervalMinutes)){
    			return;
    		}
    		AllJobs.put(jobName, jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		// 任务名，任务组，任务执行类
    		JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    		if(null!=params&&params.size()>0){
    			JobDataMap dataMap=jobDetail.getJobDataMap();
    			for(Entry<String, Object> entry:params.entrySet()){
    				String key=entry.getKey();
    				Object value=entry.getValue();
    				dataMap.put(key, value);
    			}
    		}
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
//    		triggerBuilder.withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		// 创建Trigger对象
//    		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
    		
    	/*	Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date());
    		cal.add(cal.MINUTE,intervalMinutes);
    		Date startTime=cal.getTime();*/
    		Date startTime=DateAirth.getAirthMyDate(new Date(), 0, 0, 0, 0, intervalMinutes, 0);
    		triggerBuilder.startAt(startTime);
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) triggerBuilder.build();
    		
    		// 调度容器设置JobDetail和Trigger
    		sched.scheduleJob(jobDetail, trigger);  
    		// 启动  
    		if (!sched.isShutdown()) {  
    			sched.start();  
    		}  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    	
    }
    
    /**
     * 按当前时间， 间隔毫秒数进行执行
     * intervalInSeconds 须>0
     * @Description:  
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param intervalInSeconds 间隔毫秒数>0
     * @param params
     */
    public  void addJobByCalendar(String jobName, String jobGroupName, Class<? extends Job> jobClass, long  intervalInSeconds,Map<String,Object> params) {  
    	try {  
    		if(findModifyJobExistByCalendar(jobName, jobGroupName, intervalInSeconds)){
    			return;
    		}
    		AllJobs.put(jobName, jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		// 任务名，任务组，任务执行类
    		JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    		if(null!=params&&params.size()>0){
    			JobDataMap dataMap=jobDetail.getJobDataMap();
    			for(Entry<String, Object> entry:params.entrySet()){
    				String key=entry.getKey();
    				Object value=entry.getValue();
    				dataMap.put(key, value);
    			}
    		}
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
//    		triggerBuilder.withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInSeconds(new Long(intervalInSeconds).intValue()));
    		// 创建Trigger对象
//    		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
    		
    	/*	Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date());
    		cal.add(cal.MINUTE,intervalMinutes);
    		Date startTime=cal.getTime();*/
    		Date startTime=DateAirth.getAirthMyDate(new Date(), 0, 0, 0, 0, 0, new Long(intervalInSeconds).intValue());
    		triggerBuilder.startAt(startTime);
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) triggerBuilder.build();
    		
    		// 调度容器设置JobDetail和Trigger
    		sched.scheduleJob(jobDetail, trigger);  
    		// 启动  
    		if (!sched.isShutdown()) {  
    			sched.start();  
    		}  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    	
    } 
    
    /**
     * 根据自定义的开始时间和 间隔分钟执行
     * @Description: TODO(这里用一句话描述这个方法的作用) 
     * @param jobName
     * @param jobGroupName
     * @param jobClass
     * @param startTime
     * @param intervalMinutes 间隔分钟执行
     * @param params
     */
    public  void addJobByCalendar(String jobName, String jobGroupName, Class<? extends Job> jobClass, Date  startTime,int intervalMinutes,Map<String,Object> params) {  
    	try {  
    		if(findModifyJobExistByCalendar(jobName, jobGroupName, startTime,intervalMinutes)){
    			return;
    		}
    		AllJobs.put(jobName, jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		// 任务名，任务组，任务执行类
    		JobDetail jobDetail= JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
    		if(null!=params&&params.size()>0){
    			JobDataMap dataMap=jobDetail.getJobDataMap();
    			for(Entry<String, Object> entry:params.entrySet()){
    				String key=entry.getKey();
    				Object value=entry.getValue();
    				dataMap.put(key, value);
    			}
    		}
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
//    		triggerBuilder.withSchedule(DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		// 创建Trigger对象
//    		CronTrigger trigger = (CronTrigger) triggerBuilder.build();
    		
    		/*	Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date());
    		cal.add(cal.MINUTE,intervalMinutes);
    		Date startTime=cal.getTime();*/
    		triggerBuilder.startAt(startTime);
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) triggerBuilder.build();
    		
    		// 调度容器设置JobDetail和Trigger
    		sched.scheduleJob(jobDetail, trigger);  
    		// 启动  
    		if (!sched.isShutdown()) {  
    			sched.start();  
    		}  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    	
    } 

    /** 
     * @Description: 修改一个任务的触发时间
     *  
     * @param jobName 
     * @param jobGroupName
     * @param triggerName 触发器名
     * @param triggerGroupName 触发器组名 
     * @param cron   时间设置，参考quartz说明文档   
     */  
    public  void modifyJobTime(String jobName, 
            String jobGroupName, String triggerName, String triggerGroupName, String cron) {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
            if (trigger == null) {  
                return;  
            }  
            String oldTime = trigger.getCronExpression();  
            if (!oldTime.equalsIgnoreCase(cron)) { 
                /** 方式一 ：调用 rescheduleJob 开始 */
                // 触发器  
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组  
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定  
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                sched.rescheduleJob(triggerKey, trigger);
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                //JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
                //Class<? extends Job> jobClass = jobDetail.getJobClass();  
                //removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
                //addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    /**
     * 修改触发器的调度时间
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @return
     */
    public  boolean findModifyJobExist(String jobName, 
    		String jobGroupName, String triggerName, String triggerGroupName, String cron) {  
    	boolean isExist=false;
    	try { 
    		//通过scheduleFactory来获取一个调度器
    		Scheduler sched = schedulerFactory.getScheduler(); 
    		TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
    		//返回触触发器//按照日历触发
    		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey); 
    		if (trigger == null) { 
    			return isExist;  
    		}
    		//返回触发器之前的原始的cron表达式
    		String oldTime = trigger.getCronExpression();  
    		if (!oldTime.equalsIgnoreCase(cron)) {
    			isExist=true;
    			/** 方式一 ：调用 rescheduleJob 开始 */
    			// 触发器  
    			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    			// 触发器名,触发器组  
    			triggerBuilder.withIdentity(triggerName, triggerGroupName);
    			triggerBuilder.startNow();
    			// 触发器时间设定  
    			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    			// 创建Trigger对象
    			trigger = (CronTrigger) triggerBuilder.build();
    			// 方式一 ：修改一个任务的触发时间
    			sched.rescheduleJob(triggerKey, trigger);
    			/** 方式一 ：调用 rescheduleJob 结束 */
    			
    			/** 方式二：先删除，然后在创建一个新的Job  */
    			//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
    			//Class<? extends Job> jobClass = jobDetail.getJobClass();  
    			//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
    			//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
    			/** 方式二 ：先删除，然后在创建一个新的Job */
    		}  
    		
    		isExist=true;
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}
		return isExist;  
    }
    /**
     * 修改触发器的调度时间
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @return
     */
    
    public  boolean findModifyJobExistByCron(String jobName, 
    		String jobGroupName, String cron) {  
    	boolean isExist=false;
    	try {  
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
    		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
    		if (trigger == null) { 
    			return isExist;  
    		}  
//    		String oldTime = trigger.getCronExpression();  
//    		if (!oldTime.equalsIgnoreCase(cron)) {
//    			isExist=true;
    			/** 方式一 ：调用 rescheduleJob 开始 */
    			// 触发器  
    			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    			// 触发器名,触发器组  
    			triggerBuilder.withIdentity(jobName, jobGroupName);
    			triggerBuilder.startNow();
    			// 触发器时间设定  
    			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    			// 创建Trigger对象
    			trigger = (CronTrigger) triggerBuilder.build();
    			// 方式一 ：修改一个任务的触发时间
    			sched.rescheduleJob(triggerKey, trigger);
    			/** 方式一 ：调用 rescheduleJob 结束 */
    			
    			/** 方式二：先删除，然后在创建一个新的Job  */
    			//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
    			//Class<? extends Job> jobClass = jobDetail.getJobClass();  
    			//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
    			//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
    			/** 方式二 ：先删除，然后在创建一个新的Job */
//    		}  
    		
    		isExist=true;
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}
    	return isExist;  
    }  
    /**
     * 修改触发器的调度时间
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @return
     */
    public  boolean findModifyJobExistByCron(String jobName, 
    		String jobGroupName, String cron,int delayedTime) {  
    	boolean isExist=false;
    	try {  
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
    		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
    		if (trigger == null) { 
    			return isExist;  
    		}  
//    		String oldTime = trigger.getCronExpression();  
//    		if (!oldTime.equalsIgnoreCase(cron)) {
//    			isExist=true;
    			/** 方式一 ：调用 rescheduleJob 开始 */
    			// 触发器  
    			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    			// 触发器名,触发器组  
    			triggerBuilder.withIdentity(jobName, jobGroupName);
//    			triggerBuilder.startNow();
    			Date triggerStartTime=new Date(System.currentTimeMillis()+delayedTime);
    			triggerBuilder.startAt(triggerStartTime);
    			// 触发器时间设定  
    			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
    			// 创建Trigger对象
    			trigger = (CronTrigger) triggerBuilder.build();
    			// 方式一 ：修改一个任务的触发时间
    			sched.rescheduleJob(triggerKey, trigger);
    			/** 方式一 ：调用 rescheduleJob 结束 */
    			
    			/** 方式二：先删除，然后在创建一个新的Job  */
    			//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
    			//Class<? extends Job> jobClass = jobDetail.getJobClass();  
    			//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
    			//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
    			/** 方式二 ：先删除，然后在创建一个新的Job */
//    		}  
    		
    		isExist=true;
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}
    	return isExist;  
    }  
    /**
     * 修改触发器的调度时间
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @return
     */
    public  boolean findModifyJobExistByCalendar(String jobName, 
    		String jobGroupName,int  intervalMinutes) {  
    	boolean isExist=false;
    	try {  
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
//    		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) sched.getTrigger(triggerKey);  
    		
    		if (trigger == null) { 
    			return isExist;  
    		}  
//    		String oldTime = trigger.getCronExpression();  
//    		if (!oldTime.equalsIgnoreCase(cron)) {
//    			isExist=true;
    		/** 方式一 ：调用 rescheduleJob 开始 */
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
    		triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		Date startTime=DateAirth.getAirthMyDate(new Date(), 0, 0, 0, 0, intervalMinutes, 0);
    		triggerBuilder.startAt(startTime);
    		// 创建Trigger对象
    		trigger = (CalendarIntervalTrigger) triggerBuilder.build();
//    		trigger = (CronTrigger) triggerBuilder.build();
    		// 方式一 ：修改一个任务的触发时间
    		sched.rescheduleJob(triggerKey, trigger);
    		/** 方式一 ：调用 rescheduleJob 结束 */
    		
    		/** 方式二：先删除，然后在创建一个新的Job  */
    		//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
    		//Class<? extends Job> jobClass = jobDetail.getJobClass();  
    		//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
    		//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
    		/** 方式二 ：先删除，然后在创建一个新的Job */
//    		}  
    		
    		isExist=true;
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}
    	return isExist;  
    }
    /**
     * 修改触发器的调度时间
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @return
     */
    public  boolean findModifyJobExistByCalendar(String jobName, 
    		String jobGroupName,long  intervalInSeconds) {  
    	boolean isExist=false;
    	try {  
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
//    		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) sched.getTrigger(triggerKey);  
    		
    		if (trigger == null) { 
    			return isExist;  
    		}  
//    		String oldTime = trigger.getCronExpression();  
//    		if (!oldTime.equalsIgnoreCase(cron)) {
//    			isExist=true;
    		/** 方式一 ：调用 rescheduleJob 开始 */
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
    		triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInSeconds(new Long(intervalInSeconds).intValue()));
    		Date startTime=DateAirth.getAirthMyDate(new Date(), 0, 0, 0, 0, 0, new Long(intervalInSeconds).intValue());
    		triggerBuilder.startAt(startTime);
    		// 创建Trigger对象
    		trigger = (CalendarIntervalTrigger) triggerBuilder.build();
//    		trigger = (CronTrigger) triggerBuilder.build();
    		// 方式一 ：修改一个任务的触发时间
    		sched.rescheduleJob(triggerKey, trigger);
    		/** 方式一 ：调用 rescheduleJob 结束 */
    		
    		/** 方式二：先删除，然后在创建一个新的Job  */
    		//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
    		//Class<? extends Job> jobClass = jobDetail.getJobClass();  
    		//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
    		//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
    		/** 方式二 ：先删除，然后在创建一个新的Job */
//    		}  
    		
    		isExist=true;
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}
    	return isExist;  
    }
    /**
     * 修改触发器的调度时间
     * @param jobName
     * @param jobGroupName
     * @param cron
     * @return
     */
    public  boolean findModifyJobExistByCalendar(String jobName, 
    		String jobGroupName,Date  startTime,int intervalMinutes) {  
    	boolean isExist=false;
    	try {  
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
//    		CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) sched.getTrigger(triggerKey);  
    		
    		if (trigger == null) { 
    			return isExist;  
    		}  
//    		String oldTime = trigger.getCronExpression();  
//    		if (!oldTime.equalsIgnoreCase(cron)) {
//    			isExist=true;
    		/** 方式一 ：调用 rescheduleJob 开始 */
    		// 触发器  
    		TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
    		// 触发器名,触发器组  
    		triggerBuilder.withIdentity(jobName, jobGroupName);
    		triggerBuilder.startNow();
    		// 触发器时间设定  
    		triggerBuilder.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withIntervalInMinutes(intervalMinutes));
    		triggerBuilder.startAt(startTime);
    		// 创建Trigger对象
    		trigger = (CalendarIntervalTrigger) triggerBuilder.build();
//    		trigger = (CronTrigger) triggerBuilder.build();
    		// 方式一 ：修改一个任务的触发时间
    		sched.rescheduleJob(triggerKey, trigger);
    		/** 方式一 ：调用 rescheduleJob 结束 */
    		
    		/** 方式二：先删除，然后在创建一个新的Job  */
    		//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
    		//Class<? extends Job> jobClass = jobDetail.getJobClass();  
    		//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
    		//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
    		/** 方式二 ：先删除，然后在创建一个新的Job */
//    		}  
    		
    		isExist=true;
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}
    	return isExist;  
    } 

    public  void modifyJobTime(String jobName, 
			String jobGroupName,String cron) {  
		try {  
			Scheduler sched = schedulerFactory.getScheduler();  
			TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);  
			if (trigger == null) {  
				return;  
			}  
			String oldTime = trigger.getCronExpression();  
			if (!oldTime.equalsIgnoreCase(cron)) { 
				/** 方式一 ：调用 rescheduleJob 开始 */
				// 触发器  
				TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
				// 触发器名,触发器组  
				triggerBuilder.withIdentity(jobName, jobGroupName);
				triggerBuilder.startNow();
				// 触发器时间设定  
				triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
				// 创建Trigger对象
				trigger = (CronTrigger) triggerBuilder.build();
				// 方式一 ：修改一个任务的触发时间
				sched.rescheduleJob(triggerKey, trigger);
				/** 方式一 ：调用 rescheduleJob 结束 */
				
				/** 方式二：先删除，然后在创建一个新的Job  */
				//JobDetail jobDetail = sched.getJobDetail(JobKey.jobKey(jobName, jobGroupName));  
				//Class<? extends Job> jobClass = jobDetail.getJobClass();  
				//removeJob(jobName, jobGroupName, triggerName, triggerGroupName);  
				//addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobClass, cron); 
				/** 方式二 ：先删除，然后在创建一个新的Job */
			}  
		} catch (Exception e) {  
			throw new RuntimeException(e);  
		}  
	}
    
	/** 
     * @Description: 移除一个任务 
     *  
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     */  
    public  void removeJob(String jobName, String jobGroupName,  
            String triggerName, String triggerGroupName) {  
        try {  
        	AllJobs.remove(jobName);
            Scheduler sched = schedulerFactory.getScheduler();  
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey); 
            if(null==trigger){
            	return;
            }
            sched.pauseTrigger(triggerKey);// 停止触发器  
            sched.unscheduleJob(triggerKey);// 移除触发器  
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }
    
    /** 
     * @Description: 移除一个任务 
     *  
     * @param jobName 
     * @param jobGroupName 
     * @param triggerName 
     * @param triggerGroupName 
     */  
    public  void removeJobByCalendar(String jobName, String jobGroupName) {  
    	try {  
    		AllJobs.remove(jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
    		CalendarIntervalTrigger trigger = (CalendarIntervalTrigger) sched.getTrigger(triggerKey); 
               if(null==trigger){
               	return;
               }
    		sched.pauseTrigger(triggerKey);// 停止触发器  
    		sched.unscheduleJob(triggerKey);// 移除触发器  
    		sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    }
    
    public  void removeJobByCron(String jobName, String jobGroupName) {  
    	try {  
    		AllJobs.remove(jobName);
    		Scheduler sched = schedulerFactory.getScheduler();  
    		TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroupName);
    		 CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey); 
               if(null==trigger){
               	return;
               }
    		sched.pauseTrigger(triggerKey);// 停止触发器  
    		sched.unscheduleJob(triggerKey);// 移除触发器  
    		sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));// 删除任务  
    	} catch (Exception e) {  
    		throw new RuntimeException(e);  
    	}  
    }  

    /** 
     * @Description:启动所有定时任务 
     */  
    public  void startJobs() {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            sched.start();  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  

    /** 
     * @Description:关闭所有定时任务 
     */  
    public  void shutdownJobs() {  
        try {  
            Scheduler sched = schedulerFactory.getScheduler();  
            if (!sched.isShutdown()) {  
                sched.shutdown();  
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
        }  
    }  
    
    /**
     * @Description: 暂停一个定时任务 
     * @param name
     * @param group
     */
    public void pauseJob(String name, String group){
        try {
        	 Scheduler scheduler = schedulerFactory.getScheduler(); 
            JobKey jobKey = JobKey.jobKey(name, group);
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @description: 恢复一个任务
     * @param name
     * @param group
     */
    public void resumeJob(String name, String group){
        try {
        	 Scheduler scheduler = schedulerFactory.getScheduler(); 
            JobKey jobKey = JobKey.jobKey(name, group);         
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }       
    }
    
    /**
     * @Description: 修改一个定时任务
     * @param name
     * @param group
     * @param cronExpression
     */
   
	public static Map<String, String> getAllJobs() {
		return AllJobs;
	}
	public static void setAllJobs(Map<String, String> allJobs) {
		AllJobs = allJobs;
	}
	public static SchedulerFactory getSchedulerFactory() {
		return schedulerFactory;
	}
	public static void setSchedulerFactory(SchedulerFactory schedulerFactory) {
		QuartzFactoryUtil.schedulerFactory = schedulerFactory;
	}
    
}