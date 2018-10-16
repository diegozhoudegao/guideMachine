package com.cssiot.cssbase.modules.quartz.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cssiot.cssbase.modules.quartz.data.MessageRemindModel;
import com.cssiot.cssbase.modules.quartz.job.MessageRemindJobTask;
import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.cssiot.cssbase.modules.quartz.service.SendMessageRemindService;
import com.cssiot.cssutil.common.enums.MessageRemindRepeatTypeEnum;
import com.cssiot.cssutil.common.enums.MessageRemindSendTypeEnum;
import com.cssiot.cssutil.common.exception.ResultException;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.OptionUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class SendMessageRemindServiceImpl implements SendMessageRemindService {
	

	@Autowired
	private JobService jobService;
	

	
	/**
	 * 发送提醒,表单提醒(外部调用)和一般提醒的都可以
	 * @param jsonModel 消息设定模型
	 * @param userList 立即发送时的用户集合
	 * @param loginName
	 * @param token
	 * @param clientType
	 * @author
	 * 2018年9月12日  tck 创建
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Object doSendMeassageRemind(MessageRemindModel jsonModel) {
		// 获取消息提醒的唯一标识
		String uniqueName = generateScheduleUniqueName(jsonModel);
		jsonModel.setUniqueName(uniqueName);
		// 设置参数
		Map<String,Object> params = new HashMap<>();
		switch (MessageRemindSendTypeEnum.getByValue(jsonModel.getSendType())) {
		case IMMEDIATELY:
			// 立即发送
//			sendImmediately(jsonModel, loginName, token, clientType);
			break;
		case ONCE:
			// 在时间点上执行一次(生成cron表达式,执行过一次后就删除)
			jsonModel.setRangeIdList(null);//置空是为了定时job中使用,后面会查询
			params.put("messageRemindModel", jsonModel);
			// 如果查的到定时任务,则是修改,把原来的定时任务移除掉
			jobService.deleteJob(jsonModel.getCustomerId(), uniqueName);
			sendOnceOfCron(jsonModel, params, null, null, null);
			break;
		case REPEAT:
			// 如果查的到定时任务,则是修改,把原来的定时任务移除掉
			jobService.deleteJob(jsonModel.getCustomerId(), uniqueName);
			jsonModel.setRangeIdList(null);//置空是为了定时job中使用,后面会查询
			params.put("messageRemindModel", jsonModel);
			// 加入定时任务,生成cron表达式,定时重复执行
			repeatSendOfCron(jsonModel, params);
			break;
		default:
			break;
		}
		return null;
	}
	
	/**
	 * 若是表单提醒则拼接FormRemind-formId-dataId
	 * 若是一般的提醒则拼接MessageRemind-messageRemindId
	 * @param jsonModel
	 * @return
	 * @author
	 * 2018年9月10日  tck 创建
	 */
	private String generateScheduleUniqueName(MessageRemindModel jsonModel) {
		String uniqueName = "";  
		if (!ChkUtil.isEmptyAllObject(jsonModel.getFormId())) {
			uniqueName = "FormRemind"+","+jsonModel.getFormId()+","+jsonModel.getDataId();
		} else {
			uniqueName = "MessageRemind"+","+jsonModel.getMessageRemindId();
		}
		return uniqueName;
	}
	
	
	/**
	 * 发送一次,只生成cron表达式和加入定时中
	 * @param jsonModel 消息模型
	 * @param params 参数的集合
	 * @author
	 * 2018年8月27日  tck 创建
	 */
	private void sendOnceOfCron(MessageRemindModel jsonModel, Map<String,Object> params, 
			String loginName, String token, String clientType) {
		// Date转LocalDateTime,方便计算
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime remindTime = LocalDateTime.parse(jsonModel.getRemindTime(),df);
		// 校验提前或延后的时间不能小于当前时间
		OptionUtil.of(remindTime.isBefore(LocalDateTime.now()))
			.getOrElseThrow(() -> new ResultException(-2, "发送一次设定的时间不能小于当前时间!", token, loginName, clientType));
		// 生成cron表达式
		String sendOnceOfCron = String.format("%s %s %s %s %s %s", 
				remindTime.getSecond(), remindTime.getMinute(), remindTime.getHour(), remindTime.getDayOfMonth(), "*", "?");
		// 这个定时任务执行过一次后,就根据sendType是"执行一次"进行移除
		jobService.scheduleCronJob(jsonModel.getUniqueName(), jsonModel.getCustomerId(), MessageRemindJobTask.class, sendOnceOfCron, params, true);
	}
	
	/**
	 * 重复发送,只生成cron表达式和加入定时中
	 * @param jsonModel 消息模型
	 * @param params 参数的集合
	 * @author
	 * 2018年8月27日  tck 创建
	 */
	private void repeatSendOfCron(MessageRemindModel jsonModel, Map<String,Object> params) {
		String repeatType = jsonModel.getRepeatType();//重复的时间类型
		String repeatTimePoint = jsonModel.getRepeatTimePoint();//重复的时间点
		String repeatValue = jsonModel.getRepeatValue();//具体的重复时间
		String[] hourAndMinute = repeatValue.split(":");
		// 计算cron表达式
		String repeatCron = "";
		switch (MessageRemindRepeatTypeEnum.getByValue(repeatType)) {
		case EVERYDAY:
			// 每天00:00
			repeatCron = "0 0 0 * * ?";
			break;
		case EVERYWEEK:
			// 每周几的几点几分
			repeatCron = new StringBuilder("0").append(" ")
								.append(hourAndMinute[1]).append(" ")
								.append(hourAndMinute[0]).append(" ? * ")
								.append(repeatTimePoint).toString();
			break;
		case EVERYMONTH:
			if ("30".equals(repeatTimePoint)) {// 每月的最后一天
				repeatTimePoint = "L";
			}
			// 每月某天的几点几分
			repeatCron = new StringBuilder("0").append(" ")
								.append(hourAndMinute[1]).append(" ")
								.append(hourAndMinute[0]).append(" ")
								.append(repeatTimePoint)
								.append(" * ? ").toString();
			break;
		case EVERYYEAR:
			// 每年1月1号,00:00
			repeatCron = "0 0 0 1 1 ?";
			break;
		default:
			break;
		}
		// 定时任务
		jobService.scheduleCronJob(jsonModel.getUniqueName(), jsonModel.getCustomerId(), MessageRemindJobTask.class, repeatCron, params, true);
	}
	
}
