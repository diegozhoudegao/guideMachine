package com.cssiot.cssbase.modules.quartz.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.cssiot.cssbase.modules.base.entity.Channel;
import com.cssiot.cssbase.modules.base.entity.GuideMachine;
import com.cssiot.cssbase.modules.base.service.ChannelService;
import com.cssiot.cssbase.modules.base.service.GuideMachineService;
import com.cssiot.cssbase.modules.biz.entity.RentOrder;
import com.cssiot.cssbase.modules.biz.service.RentOrderService;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssbase.modules.wxgzh.config.WxgzhConfiguration;
import com.cssiot.cssbase.modules.wxgzh.data.TemplateMsgBean;
import com.cssiot.cssbase.modules.wxgzh.service.WechatSendTemplateService;
import com.cssiot.cssutil.common.enums.StateEnum;
import com.cssiot.cssutil.common.utils.ChkUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 每天定时执行job
 * 	1、定时改变设备状态
 * 	2、异常订单发送消息给景点负责人员
 * @author 
 * 	2018-10-16 Diego.zhou 新建
 *
 */
@Slf4j
@DisallowConcurrentExecution
public class EveryDayJob implements Job {

	@Autowired
	private RentOrderService rentOrderService;
	
	@Autowired
	private WechatSendTemplateService wechatSendTemplateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WxgzhConfiguration wxgzhConfiguration;
	
	@Autowired
	private GuideMachineService guideMachineService;
	
	@Autowired
	private ChannelService channelService;
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		log.info("18点定时任务job开始执行,{}",sdf.format(new Date()));
		try {
			//1、判断改变设备状态 ， 设备状态machineStatus（0正常、2损坏、1异常、3丢失）
			//获取所有在仓的导游机，以及所有的导游机，通过比较获取所有不在仓的导游机数据，
			//若导游机的设备状态为正常，则将其改为异常，若导游机状态为异常，则将其改为丢失
			List<GuideMachine> allGuideMachine = guideMachineService.findByHql("from GuideMachine where status<>'2'");
			if(!ChkUtil.isEmptyAllObject(allGuideMachine)) {
				List<GuideMachine> errorMachineList = new ArrayList<>();
				//获取所有在仓导游机
				List<Channel> allChannelList = channelService.findByHql("from Channel where guideMachineStatus<>'2'");
				if(!ChkUtil.isEmptyAllObject(allChannelList)) {
					for(GuideMachine gm : allGuideMachine) {
						boolean isContains = false;
						for(Channel ch : allChannelList) {
							if(!ChkUtil.isEmptyAllObject(ch.getGuideMachineStatus()) && gm.getGuideMachineNo().equals(ch.getGuideMachineStatus())) {
								isContains = true;
								break;
							}
						}
						if(isContains) {
							errorMachineList.add(gm);
						}
					}
				}
				//处理有问题的导游机
				if(!ChkUtil.isEmptyAllObject(errorMachineList)) {
					for(GuideMachine gm : errorMachineList) {
						if(!ChkUtil.isEmptyAllObject(gm.getMachineStatus()) && gm.getMachineStatus().equals("0")) {
							gm.setMachineStatus("1");//异常
							guideMachineService.save(gm);
						}
						if(!ChkUtil.isEmptyAllObject(gm.getMachineStatus()) && gm.getMachineStatus().equals("1")) {
							gm.setMachineStatus("3");//丢失
							guideMachineService.save(gm);
						}
					}
				}
			}
			//2、查询锁定状态的订单，发消息给游客以及对应的景点工作人员
			List<RentOrder> rentOrderList = rentOrderService.findByHql("from RentOrder where status='"+StateEnum.DISABLESTATE.getCode()+"'");
			if(!ChkUtil.isEmptyAllObject(rentOrderList)) {
				TemplateMsgBean msgData = null;
				for(RentOrder rentOrder : rentOrderList) {
					//获取订单中的服务人员
					if(!ChkUtil.isEmptyAllObject(rentOrder.getRentUser())) {
						User user = userService.get(rentOrder.getRentUser());
						if(null != user && !ChkUtil.isEmptyAllObject(user.getOpenId())) {
							//微信公众平台发送模板消息给景点工作人员
							msgData = new TemplateMsgBean();
							Map data = new HashMap<>();// TODO 补全data内容
							msgData.setData(data);
							msgData.setOpenId(user.getOpenId());
							msgData.setTemplateId(wxgzhConfiguration.getMessageTemplateId());
							wechatSendTemplateService.doSendTemplateInfo(msgData);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("18点定时执行发生异常：{}",e);
		}
	}

}
