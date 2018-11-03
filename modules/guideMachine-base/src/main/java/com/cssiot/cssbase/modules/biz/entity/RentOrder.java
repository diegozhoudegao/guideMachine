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
import lombok.NoArgsConstructor;

/**
 * 租借订单表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_biz_rentOrder")
public class RentOrder extends CommonFields{

	@ApiModelProperty(value="订单编号")
	@Column(name="orderNo")
	private String orderNo;
	
	@ApiModelProperty(value="导游机编号")
	@Column(name="guideMachineNo")
	private String guideMachineNo;
	
	@ApiModelProperty(value="租借方式(0小程序、1身份证)")
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
	
	@ApiModelProperty(value="归还状态(0未归还、1已归还)")
	@Column(name="returnStatus")
	private String returnStatus;
	
	@ApiModelProperty(value="押金退还状态(0未退还、1已退还)")
	@Column(name="refundDepositStatus")
	private String refundDepositStatus;
	
	@ApiModelProperty(value="租金退还状态(0未退还、1已退还)")
	@Column(name="refundRentStatus")
	private String refundRentStatus;
	
	@ApiModelProperty(value="已退租金备注")
	@Column(name="refundRentRemark")
	private String refundRentRemark;
	
	@ApiModelProperty(value="已退押金备注")
	@Column(name="refundDepositRemark")
	private String refundDepositRemark;
	
	@ApiModelProperty(value="订单支付状态(0未支付、1已支付)")
	@Column(name="orderPayStatus")
	private String orderPayStatus;
	
	@ApiModelProperty(value="租借人员")
	@Column(name="rentUser")
	private String rentUser;
	
	@ApiModelProperty(value="交易编号")
	@Column(name="transactionNo")
	private String transactionNo;
	
	@ApiModelProperty(value="游客Id")
	@Column(name="visitorsId")
	private String visitorsId;
	
	@ApiModelProperty(value="景点id")
	@Column(name="scenicSpotId")
	private String scenicSpotId;
	
	@ApiModelProperty(value="机柜id")
	@Column(name="cabinetId")
	private String cabinetId;
	
	@ApiModelProperty(value="是否已经过tcp消息处理(0:是，1否)")
	@Column(name="isTcpRespHandled")
	private String isTcpRespHandled;
	
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
		this.rentUser=rentOrder.getRentUser();
		this.transactionNo=rentOrder.getTransactionNo();
	}
	
}