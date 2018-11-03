package com.cssiot.cssbase.modules.activemq;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.cssiot.cssbase.modules.activemq.data.RentRespModel;
import com.cssiot.cssbase.modules.biz.entity.RentOrder;
import com.cssiot.cssbase.modules.biz.service.RentOrderService;
import com.cssiot.cssbase.modules.quartz.service.JobService;
import com.cssiot.cssbase.modules.sys.entity.User;
import com.cssiot.cssbase.modules.sys.entity.Visitors;
import com.cssiot.cssbase.modules.sys.service.UserService;
import com.cssiot.cssbase.modules.sys.service.VisitorsService;
import com.cssiot.cssbase.modules.websocket.WebSocketServer;
import com.cssiot.cssbase.modules.websocket.WxWebSocketServer;
import com.cssiot.cssbase.modules.wxgzh.data.TemplateMsgBean;
import com.cssiot.cssbase.modules.wxgzh.service.WechatSendTemplateService;
import com.cssiot.cssutil.common.utils.ChkUtil;
import com.cssiot.cssutil.common.utils.ResponseUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * 导游机租借弹出导游机TCP响应
 * 队列名称：guideMachineRentResp_queue
	生产者：TCP
	消费者：HTTP
	消息内容：JSON格式字符串：{guideMacheineId:'导游机编号(必传)',message:'弹出失败原因(非必传)',
						code:'弹出是否成功标识(必传，0:成功，1:失败，2:异常)'}
	使用场景：针对租借中弹出指定导游机成功或失败消息
 * @author 
 * 	2018-11-01 Diego.zhou 新建
 *	
 */
@Component
@Slf4j
public class RentRespConsumer {  
       
	@Autowired
	private RentOrderService rentOrderService;
	
	@Autowired
	private JobService jobService;
	
	@Autowired
	private VisitorsService visitorsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private WechatSendTemplateService wechatSendTemplateService;
	
	private static final String RENT_GROUP_NAME = "RENT";
	
	// 使用JmsListener配置消费者监听的队列，其中text是接收到的消息  
    @JmsListener(destination = "guideMachineRentResp_queue")
    public void receiveQueue(String text) {  
        log.info("Consumer收到的报文为:{}",text);  
     	//获取消息内容
        Map queueMap = ResponseUtils.getMapJson(text);
        //订单状态(0锁定、1租借中、2完成、3失败)
        try {
        	List<RentRespModel> resultList = (List<RentRespModel>) queueMap.get("resultList");
        	if(!ChkUtil.isEmptyAllObject(resultList)) {
        		List<String> visitorsMessageList = new ArrayList<>();
        		List serviceUserMessageList = new ArrayList<>();
        		String transactionNo = null;
        		Visitors visitors = null;
        		User serviceUser = null;
        		for(RentRespModel model : resultList) {
        			RentOrder rentOrder = rentOrderService.getByHql("from RentOrder where guideMachineNo='"+model.getGuideMacheineId()+"'"
        					+ " and orderPayStatus='1' and returnStatus='0' and status='0' and isTcpRespHandled<>'0'");
        			if(null == visitors  && !ChkUtil.isEmptyAllObject(rentOrder.getVisitorsId())) {
        				visitors = visitorsService.get(rentOrder.getVisitorsId());
        			}
        			if(null == serviceUser && !ChkUtil.isEmptyAllObject(rentOrder.getRentUser())) {
        				serviceUser = userService.get(rentOrder.getRentUser());
        			}
        			if(null != rentOrder && null != visitors) {
        				rentOrder.setIsTcpRespHandled("0");
        				transactionNo = rentOrder.getTransactionNo();
        				if(!ChkUtil.isEmptyAllObject(model.getCode()) && model.getCode().equals("0")) {
        					visitorsMessageList.add("导游机"+model.getGuideMacheineId()+"租借成功");
        					//获取导游机对应的订单,将订单状态改为租借中；
        					//将订单标识改为已处理
//        					rentOrder.setIsTcpRespHandled("0");
        					rentOrder.setStatus("1");
        					rentOrderService.save(rentOrder);
        				}else if(!ChkUtil.isEmptyAllObject(model.getCode()) && model.getCode().equals("0")){//异常
        					//保持订单锁定状态，并发送失败消息给游客和此对应景点工作人员，游客可以联系工作人员，判断情况后，再解锁订单，退还押金，修改订单归还状态；
        					visitorsMessageList.add("服务器异常！");
        					//TODO 拼接发送给景点管理人员的信息
        					break;
        				}else {//将订单状态改为失败，订单费用退到账户上，发送失败消息给游客，游客可以选择取现到微信账户上；
        					visitorsMessageList.add("导游机"+model.getGuideMacheineId()+"租借失败，对应押金以及租金已退还至个人账户");
        					//TODO 拼接发送给景点管理人员的信息
        					//反写订单
        					rentOrder.setIsTcpRespHandled("3");
        					//若订单的押金状态和租金状态均为未归还时,订单费用退还游客账户
        					if(!ChkUtil.isEmptyAllObject(rentOrder.getRefundRentStatus()) && rentOrder.getRefundRentStatus().equals("0")
        							&& !ChkUtil.isEmptyAllObject(rentOrder.getRefundDepositStatus()) && rentOrder.getRefundDepositStatus().equals("0")) {
        						//获取该订单押金以及租金
        						int rentAmount = rentOrder.getRentAmount();
        						int depositAmount = rentOrder.getDepositAmount();
        						visitors.setAccountAmount(visitors.getAccountAmount()+rentAmount+depositAmount);
        						visitorsService.save(visitors);
        						rentOrder.setRefundRentStatus("1");
        						rentOrder.setRefundDepositStatus("1");
        					}
        					rentOrderService.save(rentOrder);
        				}
        			}
        		}
        		//游客发送消息
        		if(!ChkUtil.isEmptyAllObject(visitorsMessageList)) {
        			WxWebSocketServer.sendMessageTo(String.join(",", visitorsMessageList), visitors.getOpenId());
        		}
        		//景点管理人员发送消息
        		TemplateMsgBean msgBean = new TemplateMsgBean();
        		//TODO 填充数据
        		wechatSendTemplateService.doSendTemplateInfo(msgBean);
        		if(!ChkUtil.isEmptyAllObject(transactionNo)) {
        			//移除定时任务
        			boolean deleteJobIsSuccess = jobService.deleteJob(transactionNo, RENT_GROUP_NAME);
        			if (!deleteJobIsSuccess) {
        				log.error("发送一次移除定时任务失败:={},{}", transactionNo,RENT_GROUP_NAME);
        			}
        		}
        	}
        }catch(Exception e) {
        	log.error("处理租借弹出导游机异常，{}",e);
        }
    }  
}
