package com.cssiot.cssbase.modules.quartz.utils;

import java.util.Map;
import java.util.Map.Entry;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 创建可由Spring管理的定时任务
 * @ClassName: JobUtil
 * @Description: 
 * @author 
 * 2018-10-20 Diego.zhou 新建
 */
public class JobUtil {
	
	/**
	 * 创建定时任务
	 * @param jobName Job name.
	 * @param jobGroup Job group.
	 * @param jobClass Class whose executeInternal() method needs to be called. 
	 * @param params some arguments.
	 * @param isDurable Job needs to be persisted even after completion. if true, job will be persisted, not otherwise. 
	 * @param context Spring application context.
	 * 
	 * @return JobDetail object
	 */
	public static JobDetail createJob(String jobName, String jobGroupName, 
		       						  Class<? extends QuartzJobBean> jobClass, 
		       						  Map<String,Object> params, boolean isDurable, 
		       						  ApplicationContext context){
	    JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
	    factoryBean.setJobClass(jobClass);
	    factoryBean.setDurability(isDurable);
	    factoryBean.setApplicationContext(context);
	    factoryBean.setName(jobName);
	    factoryBean.setGroup(jobGroupName);
        
	    // set job data map
        JobDataMap jobDataMap = new JobDataMap();
        if(null != params && params.size() > 0) {
       	 	for(Entry<String, Object> entry : params.entrySet()){
       	 		String key = entry.getKey();
       	 		Object value = entry.getValue();
       	 		jobDataMap.put(key, value);
       	 	}
        }
        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
	    return factoryBean.getObject();
	}
}
