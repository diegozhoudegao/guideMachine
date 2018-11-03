package com.cssiot.cssbase.modules.activemq.data;

import lombok.Data;

@Data
public class RentRespModel {
//	guideMacheineId:'导游机编号(必传)',message:'弹出失败原因(非必传)',code:'弹出是否成功标识(必传，0:成功，1:失败，2:异常)'
	private String guideMacheineId;

	private String message;
	
	private String code;
}
