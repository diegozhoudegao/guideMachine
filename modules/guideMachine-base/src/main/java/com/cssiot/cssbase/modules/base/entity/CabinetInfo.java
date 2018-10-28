package com.cssiot.cssbase.modules.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssutil.common.entity.CommonFields;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机柜视图
 * @author
 *	2018-10-24 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "v_base_cabinetInfo")
public class CabinetInfo extends CommonFields{
	  
	@ApiModelProperty(value="地址")
	@Column(name="address")
	private String address;
	
	@ApiModelProperty(value="经度")
	@Column(name="longitude")
	private String longitude;
	
	@ApiModelProperty(value="维度")
	@Column(name="dimension")
	private String dimension;
	
	@ApiModelProperty(value="景点id")
	@Column(name="scenicSpotId")
	private String scenicSpotId;
	
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

	@ApiModelProperty(value="安装日期")
	@Column(name="installTime")
	private Date installTime;
	
	@ApiModelProperty(value="机柜状态(0正常、1异常)")
	@Column(name="cabinetStatus")
	private String cabinetStatus;
	
	@ApiModelProperty(value="仓道数")
	@Column(name="channelNumber")
	private Integer channelNumber;
	
	@ApiModelProperty(value="APK版本")
	@Column(name="hardwareVersion")
	private String hardwareVersion;
	
	@ApiModelProperty(value="硬件版本")
	@Column(name="softVersion")
	private String softVersion;

}