package com.cssiot.cssbase.modules.base.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.base.data.GuideMachineModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 导游机表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_base_guideMachine")
public class GuideMachine extends CommonFields{

	@ApiModelProperty(value="机柜id")
	@Column(name="cabinetId")
	private String cabinetId;
	
	@ApiModelProperty(value="仓道id")
	@Column(name="channelId")
	private String channelId;
	
	@ApiModelProperty(value="导游机IMEI")
	@Column(name="imetNo")
	private String imetNo;
	
	@ApiModelProperty(value="导游机编号")
	@Column(name="guideMachineNo")
	private String guideMachineNo;
	
	@ApiModelProperty(value="机型")
	@Column(name="guideMachineType")
	private String guideMachineType;

	@ApiModelProperty(value="安装日期")
	@Column(name="installTime")
	private Date installTime;
	
	@ApiModelProperty(value="温度状态(正常数值、-1异常)")
	@Column(name="temperatureStatus")
	private String temperatureStatus;
	
	@ApiModelProperty(value="电量状态(正常数值、-1异常)")
	@Column(name="electricityStatus")
	private String electricityStatus;
	
	@ApiModelProperty(value="设备状态(0正常、2损坏、1异常、3丢失)")
	@Column(name="machineStatus")
	private String machineStatus;
	
	@ApiModelProperty(value="租用状态(0可租、2不可租、1已租)")
	@Column(name="rentStatus")
	private String rentStatus;
	
	@ApiModelProperty(value="位置状态(0正常、1异常、2空)")
	@Column(name="positionStatus")
	private String positionStatus;
	
	public GuideMachine(GuideMachineModel guideMachine) {
		super();
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