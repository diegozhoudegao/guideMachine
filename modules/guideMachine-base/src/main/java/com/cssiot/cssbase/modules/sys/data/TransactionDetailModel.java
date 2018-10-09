package com.cssiot.cssbase.modules.sys.data;

import com.cssiot.cssbase.modules.sys.entity.TransactionDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 交易明细表信息
 * @author
 *	2018-10-05 athena 创建
 */
@Data
public class TransactionDetailModel {
	
	@ApiModelProperty(value="交易明细id")
	private String transactionDetailId;
	
	@ApiModelProperty(value="游客id")
	private String visitorsId;
	
	@ApiModelProperty(value="交易金额")
	private Integer transactioAmount;
	
	@ApiModelProperty(value="交易类型(充值、提现、退款)")
	private String transactioType;

	@ApiModelProperty(value="方式(微信、支付宝)")
	private String transactioMode;

	public TransactionDetailModel(TransactionDetail transactionDetail) {
		super();
		this.transactionDetailId = transactionDetail.getId();
		this.visitorsId = transactionDetail.getVisitorsId();
		this.transactioAmount = transactionDetail.getTransactioAmount();
		this.transactioType = transactionDetail.getTransactioType();
		this.transactioMode = transactionDetail.getTransactioMode();
	}

}