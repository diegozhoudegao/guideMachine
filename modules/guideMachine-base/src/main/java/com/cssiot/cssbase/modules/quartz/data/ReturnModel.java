package com.cssiot.cssbase.modules.quartz.data;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 归还信息
 * @author 
 * 	2018-11-01 Diego.zhou 新建
 *
 */
@Data
public class ReturnModel {


	@ApiModelProperty(value="交易编号")
	private String transactionNo;
	
	@ApiModelProperty(value="导游机编号集合")
	private List<String> guideMachineNoList;
}
