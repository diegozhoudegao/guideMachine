package com.cssiot.cssbase.modules.biz.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.biz.data.RentOrderModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租借订单表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_biz_rentOrder")
public class RentOrder extends CommonFields{

	@ApiModelProperty(value="订单编号")
	@Column(name="orderNo")
	private String orderNo;
	
	@ApiModelProperty(value="导游机编号")
	@Column(name="guideMachineNo")
	private String guideMachineNo;
	
	@ApiModelProperty(value="租借方式(小程序、身份证)")
	@Column(name="rentType")
	private String rentType;
	
	@ApiModelProperty(value="手机")
	@Column(name="phone")
	private String phone;
	
	@ApiModelProperty(value="身份证")
	@Column(name="identityCard")
	private String identityCard;

	@ApiModelProperty(value="租借时间")
	@Column(name="rentTime")
	private Date rentTime;
	
	@ApiModelProperty(value="归还时间")
	@Column(name="returnTime")
	private Date returnTime;
	
	@ApiModelProperty(value="租金")
	@Column(name="rentAmount")
	private Integer rentAmount;
	
	@ApiModelProperty(value="押金")
	@Column(name="depositAmount")
	private Integer depositAmount;
	
	@ApiModelProperty(value="归还状态(未归还、已归还)")
	@Column(name="returnStatus")
	private String returnStatus;
	
	@ApiModelProperty(value="押金退还状态(未退还、已退还)")
	@Column(name="refundDepositStatus")
	private String refundDepositStatus;
	
	@ApiModelProperty(value="租金退还状态(未退还、已退还)")
	@Column(name="refundRentStatus")
	private String refundRentStatus;
	
	@ApiModelProperty(value="已退租金备注")
	@Column(name="refundRentRemark")
	private String refundRentRemark;
	
	@ApiModelProperty(value="已退押金备注")
	@Column(name="refundDepositRemark")
	private String refundDepositRemark;
	
	@ApiModelProperty(value="订单支付状态(未支付、已支付)")
	@Column(name="orderPayStatus")
	private String orderPayStatus;
	
	public RentOrder(RentOrderModel rentOrder) {
		super();
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