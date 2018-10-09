package com.cssiot.cssbase.modules.sys.data;

import java.util.Date;
import com.cssiot.cssbase.modules.sys.entity.RentHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 租借历史表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class RentHistoryModel {
	
	@ApiModelProperty(value="租借历史id")
	private String rentHistoryId;
	
	@ApiModelProperty(value="游客id")
	private String visitorsId;
	
	@ApiModelProperty(value="订单编号")
	private Date orderNo;
	
	@ApiModelProperty(value="省份")
	private String province;

	@ApiModelProperty(value="城市")
	private String city;
	
	@ApiModelProperty(value="区县")
	private String county;
	
	@ApiModelProperty(value="景点id")
	private String scenicSpotId;
	
	@ApiModelProperty(value="导游机编号")
	private String guideMachineNo;
	
	@ApiModelProperty(value="租金")
	private Integer rentAmount;
	
	@ApiModelProperty(value="押金")
	private Integer depositAmount;
	
	@ApiModelProperty(value="租借时间")
	private Date rentTime;
	
	@ApiModelProperty(value="归还时间")
	private Date returnTime;
	
	@ApiModelProperty(value="已退租金备注")
	private String refundRentRemark;
	
	@ApiModelProperty(value="已退押金备注")
	private String refundDepositRemark;
	
	@ApiModelProperty(value="服务人员")
	private String userIds;

	public RentHistoryModel(RentHistory rentHistory) {
		super();
		this.rentHistoryId = rentHistory.getId();
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