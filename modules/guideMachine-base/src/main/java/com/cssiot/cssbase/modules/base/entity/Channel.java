package com.cssiot.cssbase.modules.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.base.data.ChannelModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 仓道表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_base_channel")
public class Channel extends CommonFields{

	@ApiModelProperty(value="机柜id")
	@Column(name="cabinetId")
	private String cabinetId;
	
	@ApiModelProperty(value="仓道状态(正常、异常)")
	@Column(name="channelStatus")
	private String channelStatus;
	
	@ApiModelProperty(value="仓道在机柜位置")
	@Column(name="position")
	private String position;
	
	@ApiModelProperty(value="导游机在仓状态(在仓则显示导游机编号、空仓)")
	@Column(name="guideMachineStatus")
	private String guideMachineStatus;

	public Channel(ChannelModel channel) {
		super();
		this.cabinetId = channel.getCabinetId();
		this.channelStatus = channel.getChannelStatus();
		this.position = channel.getPosition();
		this.guideMachineStatus = channel.getGuideMachineStatus();
	}
}