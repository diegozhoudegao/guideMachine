package com.cssiot.cssbase.modules.base.data;

import javax.persistence.Column;
import com.cssiot.cssbase.modules.base.entity.ScenicSpotEntrance;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 景点出入口表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class ScenicSpotEntranceModel {
	
	@ApiModelProperty(value="景点出入口id")
	private String scenicSpotEntranceId;
	
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
	
	@ApiModelProperty(value="类型(0出口、1入口)")
	@Column(name="entranceType")
	private String entranceType;

	public ScenicSpotEntranceModel(ScenicSpotEntrance scenicSpotEntrance) {
		super();
		this.scenicSpotEntranceId = scenicSpotEntrance.getId();
		this.scenicSpotId = scenicSpotEntrance.getScenicSpotId();
		this.address = scenicSpotEntrance.getAddress();
		this.longitude = scenicSpotEntrance.getLongitude();
		this.dimension = scenicSpotEntrance.getDimension();
		this.entranceType = scenicSpotEntrance.getEntranceType();
	}

}