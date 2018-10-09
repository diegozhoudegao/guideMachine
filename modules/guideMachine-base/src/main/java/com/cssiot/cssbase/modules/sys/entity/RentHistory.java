package com.cssiot.cssbase.modules.sys.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssbase.modules.sys.data.RentHistoryModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租借历史表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_rentHistory")
public class RentHistory extends CommonFields{

	@ApiModelProperty(value="游客id")
	@Column(name="visitorsId")
	private String visitorsId;
	
	@ApiModelProperty(value="订单编号")
	@Column(name="orderNo")
	private Date orderNo;
	
	@ApiModelProperty(value="省份")
	@Column(name="province")
	private String province;

	@ApiModelProperty(value="城市")
	@Column(name="city")
	private String city;
	
	@ApiModelProperty(value="区县")
	@Column(name="county")
	private String county;
	
	@ApiModelProperty(value="景点id")
	@Column(name="scenicSpotId")
	private String scenicSpotId;
	
	@ApiModelProperty(value="导游机编号")
	@Column(name="guideMachineNo")
	private String guideMachineNo;
	
	@ApiModelProperty(value="租金")
	@Column(name="rentAmount")
	private Integer rentAmount;
	
	@ApiModelProperty(value="押金")
	@Column(name="depositAmount")
	private Integer depositAmount;
	
	@ApiModelProperty(value="租借时间")
	@Column(name="rentTime")
	private Date rentTime;
	
	@ApiModelProperty(value="归还时间")
	@Column(name="returnTime")
	private Date returnTime;
	
	@ApiModelProperty(value="已退租金备注")
	@Column(name="refundRentRemark")
	private String refundRentRemark;
	
	@ApiModelProperty(value="已退押金备注")
	@Column(name="refundDepositRemark")
	private String refundDepositRemark;
	
	@ApiModelProperty(value="服务人员")
	@Column(name="userIds")
	private String userIds;
	
	public RentHistory(RentHistoryModel rentHistory) {
		super();
		this.visitorsId = rentHistory.getVisitorsId();
		this.orderNo = rentHistory.getOrderNo();
		this.province = rentHistory.getProvince();
		this.city = rentHistory.getCity();
		this.county = rentHistory.getCounty();
		this.scenicSpotId = rentHistory.getScenicSpotId();
		this.guideMachineNo = rentHistory.getGuideMachineNo();
		this.rentAmount = rentHistory.getRentAmount();
		this.depositAmount = rentHistory.getDepositAmount();
		this.rentTime = rentHistory.getRentTime();
		this.returnTime = rentHistory.getReturnTime();
		this.refundRentRemark = rentHistory.getRefundRentRemark();
		this.refundDepositRemark = rentHistory.getRefundDepositRemark();
		this.userIds = rentHistory.getUserIds();
	}
	
}