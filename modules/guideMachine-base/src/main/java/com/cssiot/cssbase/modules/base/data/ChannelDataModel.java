package com.cssiot.cssbase.modules.base.data;

import com.cssiot.cssbase.modules.base.entity.ChannelInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仓道和导游机信息
 * @author
 *	2018-10-22 athena 创建
 */
@Data
public class ChannelDataModel {
	
	@ApiModelProperty(value="仓道id")
	private String channelId;
	
	@ApiModelProperty(value="机柜id")
	private String cabinetId;
	
	@ApiModelProperty(value="仓道状态(0正常、1异常)")
	private String channelStatus;
	
	@ApiModelProperty(value="仓道在机柜位置")
	private String position;
	
	@ApiModelProperty(value="导游机在仓状态(在仓则显示导游机编号、2空仓)")
	private String guideMachineStatus;
	
	@ApiModelProperty(value="导游机IMEI")
	private String imetNo;
	
	@ApiModelProperty(value="导游机编号")
	private String guideMachineNo;
	
	@ApiModelProperty(value="温度状态(正常数值、-1异常)")
	private String temperatureStatus;
	
	@ApiModelProperty(value="电量状态(正常数值、-1异常)")
	private String electricityStatus;
	
	@ApiModelProperty(value="设备状态(0正常、2损坏、1异常、3丢失)")
	private String machineStatus;
	
	@ApiModelProperty(value="租用状态(0可租、2不可租、1已租)")
	private String rentStatus;
	
	@ApiModelProperty(value="位置状态(0正常、1异常、2空)")
	private String positionStatus;

	public ChannelDataModel(ChannelInfo channelInfo) {
		super();
		this.channelId = channelInfo.getId();
		this.cabinetId = channelInfo.getCabinetId();
		this.channelStatus = channelInfo.getChannelStatus();
		this.position = channelInfo.getPosition();
		this.imetNo = channelInfo.getImetNo();
		this.guideMachineNo = channelInfo.getGuideMachineNo();
		this.temperatureStatus = channelInfo.getTemperatureStatus();
		this.electricityStatus = channelInfo.getElectricityStatus();
		this.machineStatus = channelInfo.getMachineStatus();
		this.rentStatus = channelInfo.getRentStatus();
		this.positionStatus = channelInfo.getPositionStatus();
	}

}