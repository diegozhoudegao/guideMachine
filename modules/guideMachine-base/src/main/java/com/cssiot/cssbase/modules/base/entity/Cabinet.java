package com.cssiot.cssbase.modules.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.cssiot.cssbase.modules.base.data.CabinetModel;
import com.cssiot.cssutil.common.entity.CommonFields;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机柜表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_base_cabinet")
public class Cabinet extends CommonFields{
	  
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

	@ApiModelProperty(value="安装日期")
	@Column(name="installTime")
	private Date installTime;
	
	@ApiModelProperty(value="机柜状态(正常、异常)")
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
	
	public Cabinet(CabinetModel cabinet) {
		super();
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