package com.cssiot.cssbase.modules.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仓道导游机信息
 * @author
 *	2018-10-15 athena 创建
 */
@Entity
@Data
@Table(name = "v_base_channelInfo")
public class ChannelInfo{
	@Id
	@ApiModelProperty(value="仓道id")
	@Column(name="id")
	private String id;
	
	@ApiModelProperty(value="机柜id")
	@Column(name="cabinetId")
	private String cabinetId;
	
	@ApiModelProperty(value="仓道状态(0正常、1异常)")
	@Column(name="channelStatus")
	private String channelStatus;
	
	@ApiModelProperty(value="仓道在机柜位置")
	@Column(name="position")
	private String position;
	
	@ApiModelProperty(value="导游机在仓状态(在仓则显示导游机编号、2空仓)")
	@Column(name="guideMachineStatus")
	private String guideMachineStatus;
	
	@ApiModelProperty(value="导游机IMEI")
	@Column(name="imetNo")
	private String imetNo;
	
	@ApiModelProperty(value="导游机编号")
	@Column(name="guideMachineNo")
	private String guideMachineNo;
	
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
	
}