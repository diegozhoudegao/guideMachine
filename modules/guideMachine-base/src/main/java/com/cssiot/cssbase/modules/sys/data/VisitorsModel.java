package com.cssiot.cssbase.modules.sys.data;

import java.util.Date;
import com.cssiot.cssbase.modules.sys.entity.Visitors;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 游客表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class VisitorsModel {
	
	@ApiModelProperty(value="游客id")
	private String visitorsId;
	
	@ApiModelProperty(value="游客等级(0普通、1VIP)")
	private String visitorsType;
	
	@ApiModelProperty(value="注册日期")
	private Date registerTime;
	
	@ApiModelProperty(value="注册省份")
	private String province;

	@ApiModelProperty(value="注册城市")
	private String city;
	
	@ApiModelProperty(value="注册区县")
	private String county;
	
	@ApiModelProperty(value="注册景点id")
	private String scenicSpotId;
	
	@ApiModelProperty(value="手机")
	private String phone;
	
	@ApiModelProperty(value="身份证")
	private String identityCard;

	@ApiModelProperty(value="微信号")
	private String wechatNo;
	
	@ApiModelProperty(value="支付宝号")
	private String alipayNo;
	
	@ApiModelProperty(value="姓名")
	private String userName;
	
	@ApiModelProperty(value="性别")
	private String sex;
	
	@ApiModelProperty(value="籍贯")
	private String nativePlace;
	
	@ApiModelProperty(value="民族")
	private String nation;
	
	@ApiModelProperty(value="出生年月")
	private String birthDate;
	
	@ApiModelProperty(value="家庭地址")
	private String homeAddress;
	
	@ApiModelProperty(value="租借次数")
	private Integer rentNumber;
	
	@ApiModelProperty(value="是否黑名单(0是、1否)")
	private String isBlacklist;
	
	@ApiModelProperty(value="最近租借日期")
	private Date lastRentTime;
	
	@ApiModelProperty(value="导游机编号")
	private String guideMachineNo;
	
	@ApiModelProperty(value="归还状态(0未归还、1已归还)")
	private String returnStatus;
	
	@ApiModelProperty(value="VIP说明")
	private String vipRemark;
	
	@ApiModelProperty(value="账户")
	private Integer accountAmount;
	
	@ApiModelProperty(value="小程序标识")
	private String openId;

	public VisitorsModel(Visitors visitors) {
		super();
		this.visitorsId = visitors.getId();
		this.visitorsType = visitors.getVisitorsType();
		this.registerTime = visitors.getRegisterTime();
		this.province = visitors.getProvince();
		this.city = visitors.getCity();
		this.county = visitors.getCounty();
		this.scenicSpotId = visitors.getScenicSpotId();
		this.phone = visitors.getPhone();
		this.identityCard = visitors.getIdentityCard();
		this.wechatNo = visitors.getWechatNo();
		this.alipayNo = visitors.getAlipayNo();
		this.userName = visitors.getUserName();
		this.sex = visitors.getSex();
		this.nativePlace = visitors.getNativePlace();
		this.nation = visitors.getNation();
		this.birthDate = visitors.getBirthDate();
		this.homeAddress = visitors.getHomeAddress();
		this.rentNumber = visitors.getRentNumber();
		this.isBlacklist = visitors.getIsBlacklist();
		this.lastRentTime = visitors.getLastRentTime();
		this.guideMachineNo = visitors.getGuideMachineNo();
		this.returnStatus = visitors.getReturnStatus();
		this.vipRemark = visitors.getVipRemark();
		this.accountAmount = visitors.getAccountAmount();
		this.openId = visitors.getOpenId();
	}

}