package com.cssiot.cssbase.modules.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssbase.modules.base.data.ScenicSpotEntranceModel;
import com.cssiot.cssutil.common.entity.CommonFields;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 景点出入口表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_base_scenicSpotEntrance")
public class ScenicSpotEntrance extends CommonFields{
	
	@ApiModelProperty(value="景点id")
	@Column(name="scenicSpotId")
	private String scenicSpotId;
	
	@ApiModelProperty(value="地址")
	@Column(name="address")
	private String address;
	
	@ApiModelProperty(value="经度")
	@Column(name="longitude")
	private String longitude;
	
	@ApiModelProperty(value="维度")
	@Column(name="dimension")
	private String dimension;
	
	@ApiModelProperty(value="类型(出口、入口)")
	@Column(name="entranceType")
	private String entranceType;

	public ScenicSpotEntrance(ScenicSpotEntranceModel scenicSpotEntrance) {
		super();
		this.scenicSpotId = scenicSpotEntrance.getScenicSpotId();
		this.address = scenicSpotEntrance.getAddress();
		this.longitude = scenicSpotEntrance.getLongitude();
		this.dimension = scenicSpotEntrance.getDimension();
		this.entranceType = scenicSpotEntrance.getEntranceType();
	}
	
}