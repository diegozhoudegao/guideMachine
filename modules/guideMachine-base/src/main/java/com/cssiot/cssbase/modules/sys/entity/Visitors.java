package com.cssiot.cssbase.modules.sys.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.sys.data.VisitorsModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 游客表
 * @author
 *	2018-10-05 athena 创建
 *	2018-10-14 Diego.zhou 增加小程序专用的sessionKey和sKey字段
 */
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_visitors")
public class Visitors extends CommonFields{

	@ApiModelProperty(value="游客等级(0普通、1VIP)")
	@Column(name="visitorsType")
	private String visitorsType;
	
	@ApiModelProperty(value="注册日期")
	@Column(name="registerTime")
	private Date registerTime;
	
	@ApiModelProperty(value="注册省份")
	@Column(name="province")
	private String province;

	@ApiModelProperty(value="注册城市")
	@Column(name="city")
	private String city;
	
	@ApiModelProperty(value="注册区县")
	@Column(name="county")
	private String county;
	
	@ApiModelProperty(value="注册景点id")
	@Column(name="scenicSpotId")
	private String scenicSpotId;
	
	@ApiModelProperty(value="手机")
	@Column(name="phone")
	private String phone;
	
	@ApiModelProperty(value="身份证")
	@Column(name="identityCard")
	private String identityCard;

	@ApiModelProperty(value="微信号")
	@Column(name="wechatNo")
	private String wechatNo;
	
	@ApiModelProperty(value="支付宝号")
	@Column(name="alipayNo")
	private String alipayNo;
	
	@ApiModelProperty(value="姓名")
	@Column(name="userName")
	private String userName;
	
	@ApiModelProperty(value="性别")
	@Column(name="sex")
	private String sex;
	
	@ApiModelProperty(value="籍贯")
	@Column(name="nativePlace")
	private String nativePlace;
	
	@ApiModelProperty(value="民族")
	@Column(name="nation")
	private String nation;
	
	@ApiModelProperty(value="出生年月")
	@Column(name="birthDate")
	private String birthDate;
	
	@ApiModelProperty(value="家庭地址")
	@Column(name="homeAddress")
	private String homeAddress;
	
	@ApiModelProperty(value="租借次数")
	@Column(name="rentNumber")
	private Integer rentNumber;
	
	@ApiModelProperty(value="是否黑名单(0是、1否)")
	@Column(name="isBlacklist")
	private String isBlacklist;
	
	@ApiModelProperty(value="最近租借日期")
	@Column(name="lastRentTime")
	private Date lastRentTime;
	
	@ApiModelProperty(value="导游机编号")
	@Column(name="guideMachineNo")
	private String guideMachineNo;
	
	@ApiModelProperty(value="归还状态(0未归还、1已归还)")
	@Column(name="returnStatus")
	private String returnStatus;
	
	@ApiModelProperty(value="VIP说明")
	@Column(name="vipRemark")
	private String vipRemark;
	
	@ApiModelProperty(value="账户")
	@Column(name="accountAmount")
	private Integer accountAmount;
	
	@ApiModelProperty(value="用户小程序用户对应openId")
	@Column(name="openId")
	private String openId;
	
	@ApiModelProperty(value="用户小程序自定义登录态")
	@Column(name="sKey")
	private String sKey;
	
	@ApiModelProperty(value="用户小程序sessionKey")
	@Column(name="sessionKey")
	private String sessionKey;
	
	public Visitors(VisitorsModel visitors,String userId) {
		super();
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