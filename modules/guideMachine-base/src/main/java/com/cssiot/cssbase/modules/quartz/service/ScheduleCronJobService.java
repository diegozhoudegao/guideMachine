package com.cssiot.cssbase.modules.quartz.service;

import com.cssiot.cssbase.modules.quartz.data.RentModel;
import com.cssiot.cssbase.modules.quartz.data.ReturnModel;

public interface ScheduleCronJobService {

	/**
	 * 订单租借时新增定时任务
	 * @param rentModel
	 */
	public void doScheduleRentJob(RentModel rentModel);
	
	/**
	 * 扫码归还时新增定时任务
	 * @param returnModel
	 */
	public void doScheduleReturnJob(ReturnModel returnModel);
}
