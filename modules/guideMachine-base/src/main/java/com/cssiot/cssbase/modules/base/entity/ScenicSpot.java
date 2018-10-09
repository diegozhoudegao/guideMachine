package com.cssiot.cssbase.modules.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.base.data.ScenicSpotModel;
import com.cssiot.cssutil.common.entity.CommonFields;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 景点主表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_base_scenicSpot")
public class ScenicSpot extends CommonFields{

	@ApiModelProperty(value="地址")
	@Column(name="address")
	private String address;
	
	@ApiModelProperty(value="经度")
	@Column(name="longitude")
	private String longitude;
	
	@ApiModelProperty(value="维度")
	@Column(name="dimension")
	private String dimension;
	
	@ApiModelProperty(value="省份")
	@Column(name="province")
	private String province;

	@ApiModelProperty(value="城市")
	@Column(name="city")
	private String city;
	
	@ApiModelProperty(value="区县")
	@Column(name="county")
	private String county;
	
	@ApiModelProperty(value="景点名称")
	@Column(name="scenicSpotName")
	private String scenicSpotName;
	
	@ApiModelProperty(value="租金")
	@Column(name="rentAmount")
	private Integer rentAmount;
	
	@ApiModelProperty(value="押金")
	@Column(name="depositAmount")
	private Integer depositAmount;
	
	@ApiModelProperty(value="公司id")
	@Column(name="companyId")
	private String companyId;
	
	public ScenicSpot(ScenicSpotModel scenicSpot) {
		super();
		this.address = scenicSpot.getAddress();
		this.longitude = scenicSpot.getLongitude();
		this.dimension = scenicSpot.getDimension();
		this.province = scenicSpot.getProvince();
		this.city = scenicSpot.getCity();
		this.county = scenicSpot.getCounty();
		this.scenicSpotName = scenicSpot.getScenicSpotName();
		this.rentAmount = scenicSpot.getRentAmount();
		this.depositAmount = scenicSpot.getDepositAmount();
		this.companyId=scenicSpot.getCompanyId();
	}
	
}