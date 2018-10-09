package com.cssiot.cssbase.modules.base.data;

import com.cssiot.cssbase.modules.base.entity.ScenicSpot;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 景点主表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class ScenicSpotModel {
	
	@ApiModelProperty(value="景点id")
	private String scenicSpotId;
	
	@ApiModelProperty(value="地址")
	private String address;
	
	@ApiModelProperty(value="经度")
	private String longitude;
	
	@ApiModelProperty(value="维度")
	private String dimension;
	
	@ApiModelProperty(value="省份")
	private String province;

	@ApiModelProperty(value="城市")
	private String city;
	
	@ApiModelProperty(value="区县")
	private String county;
	
	@ApiModelProperty(value="景点名称")
	private String scenicSpotName;
	
	@ApiModelProperty(value="租金")
	private Integer rentAmount;
	
	@ApiModelProperty(value="押金")
	private Integer depositAmount;
	
	@ApiModelProperty(value="公司id")
	private String companyId;

	public ScenicSpotModel(ScenicSpot scenicSpot) {
		super();
		this.scenicSpotId = scenicSpot.getId();
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