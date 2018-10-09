package com.cssiot.cssbase.modules.base.data;

import java.util.Date;

import com.cssiot.cssbase.modules.base.entity.Cabinet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 机柜表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class CabinetModel {
	
	@ApiModelProperty(value="机柜id")
	private String cabinetId;
	
	@ApiModelProperty(value="地址")
	private String address;
	
	@ApiModelProperty(value="经度")
	private String longitude;
	
	@ApiModelProperty(value="维度")
	private String dimension;
	
	@ApiModelProperty(value="景点id")
	private String scenicSpotId;

	@ApiModelProperty(value="安装日期")
	private Date installTime;
	
	@ApiModelProperty(value="机柜状态(正常、异常)")
	private String cabinetStatus;
	
	@ApiModelProperty(value="仓道数")
	private Integer channelNumber;
	
	@ApiModelProperty(value="APK版本")
	private String hardwareVersion;
	
	@ApiModelProperty(value="硬件版本")
	private String softVersion;

	public CabinetModel(Cabinet cabinet) {
		super();
		this.cabinetId=cabinet.getId();
		this.address = cabinet.getAddress();
		this.longitude = cabinet.getLongitude();
		this.dimension = cabinet.getDimension();
		this.scenicSpotId = cabinet.getScenicSpotId();
		this.installTime = cabinet.getInstallTime();
		this.cabinetStatus = cabinet.getCabinetStatus();
		this.channelNumber = cabinet.getChannelNumber();
		this.hardwareVersion = cabinet.getHardwareVersion();
		this.softVersion = cabinet.getSoftVersion();
	}
	
}