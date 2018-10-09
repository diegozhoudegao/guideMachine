package com.cssiot.cssbase.modules.biz.data;

import java.util.Date;
import com.cssiot.cssbase.modules.biz.entity.RentOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 租借订单表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class RentOrderModel {
	
	@ApiModelProperty(value="订单id")
	private String rentOrderId;
	
	@ApiModelProperty(value="订单编号")
	private String orderNo;
	
	@ApiModelProperty(value="导游机编号")
	private String guideMachineNo;
	
	@ApiModelProperty(value="租借方式(小程序、身份证)")
	private String rentType;
	
	@ApiModelProperty(value="手机")
	private String phone;
	
	@ApiModelProperty(value="身份证")
	private String identityCard;

	@ApiModelProperty(value="租借时间")
	private Date rentTime;
	
	@ApiModelProperty(value="归还时间")
	private Date returnTime;
	
	@ApiModelProperty(value="租金")
	private Integer rentAmount;
	
	@ApiModelProperty(value="押金")
	private Integer depositAmount;
	
	@ApiModelProperty(value="归还状态(未归还、已归还)")
	private String returnStatus;
	
	@ApiModelProperty(value="押金退还状态(未退还、已退还)")
	private String refundDepositStatus;
	
	@ApiModelProperty(value="租金退还状态(未退还、已退还)")
	private String refundRentStatus;
	
	@ApiModelProperty(value="已退租金备注")
	private String refundRentRemark;
	
	@ApiModelProperty(value="已退押金备注")
	private String refundDepositRemark;
	
	@ApiModelProperty(value="订单支付状态(未支付、已支付)")
	private String orderPayStatus;

	public RentOrderModel(RentOrder rentOrder) {
		super();
		this.rentOrderId = rentOrder.getId();
		this.orderNo = rentOrder.getOrderNo();
		this.guideMachineNo = rentOrder.getGuideMachineNo();
		this.rentType = rentOrder.getRentType();
		this.phone = rentOrder.getPhone();
		this.identityCard = rentOrder.getIdentityCard();
		this.rentTime = rentOrder.getRentTime();
		this.returnTime = rentOrder.getReturnTime();
		this.rentAmount = rentOrder.getRentAmount();
		this.depositAmount = rentOrder.getDepositAmount();
		this.returnStatus = rentOrder.getReturnStatus();
		this.refundDepositStatus = rentOrder.getRefundDepositStatus();
		this.refundRentStatus = rentOrder.getRefundRentStatus();
		this.refundRentRemark = rentOrder.getRefundRentRemark();
		this.refundDepositRemark = rentOrder.getRefundDepositRemark();
		this.orderPayStatus = rentOrder.getOrderPayStatus();
	}
	
}