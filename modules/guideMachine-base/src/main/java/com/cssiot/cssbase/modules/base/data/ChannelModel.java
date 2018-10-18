package com.cssiot.cssbase.modules.base.data;

import com.cssiot.cssbase.modules.base.entity.Channel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仓道表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class ChannelModel {
	
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

	public ChannelModel(Channel channel) {
		super();
		this.channelId = channel.getId();
		this.cabinetId = channel.getCabinetId();
		this.channelStatus = channel.getChannelStatus();
		this.position = channel.getPosition();
		this.guideMachineStatus = channel.getGuideMachineStatus();
	}

}