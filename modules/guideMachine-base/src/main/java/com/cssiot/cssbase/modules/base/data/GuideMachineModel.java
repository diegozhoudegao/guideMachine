package com.cssiot.cssbase.modules.base.data;

import java.util.Date;
import com.cssiot.cssbase.modules.base.entity.GuideMachine;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 导游机表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class GuideMachineModel {
	
	@ApiModelProperty(value="导游机id")
	private String guideMachineId;
	
	@ApiModelProperty(value="机柜id")
	private String cabinetId;
	
	@ApiModelProperty(value="仓道id")
	private String channelId;
	
	@ApiModelProperty(value="导游机IMEI")
	private String imetNo;
	
	@ApiModelProperty(value="导游机编号")
	private String guideMachineNo;
	
	@ApiModelProperty(value="机型")
	private String guideMachineType;

	@ApiModelProperty(value="安装日期")
	private Date installTime;
	
	@ApiModelProperty(value="温度状态(正常、异常)")
	private String temperatureStatus;
	
	@ApiModelProperty(value="电量状态(正常数值、异常)")
	private String electricityStatus;
	
	@ApiModelProperty(value="设备状态(正常、损坏、异常、丢失)")
	private String machineStatus;
	
	@ApiModelProperty(value="租用状态(可租、不可租、已租)")
	private String rentStatus;
	
	@ApiModelProperty(value="位置状态(正常、异常、空)")
	private String positionStatus;

	public GuideMachineModel(GuideMachine guideMachine) {
		super();
		this.guideMachineId = guideMachine.getId();
		this.cabinetId = guideMachine.getCabinetId();
		this.channelId = guideMachine.getChannelId();
		this.imetNo = guideMachine.getImetNo();
		this.guideMachineNo = guideMachine.getGuideMachineNo();
		this.guideMachineType = guideMachine.getGuideMachineType();
		this.installTime = guideMachine.getInstallTime();
		this.temperatureStatus = guideMachine.getTemperatureStatus();
		this.electricityStatus = guideMachine.getElectricityStatus();
		this.machineStatus = guideMachine.getMachineStatus();
		this.rentStatus = guideMachine.getRentStatus();
		this.positionStatus = guideMachine.getPositionStatus();
	}

}