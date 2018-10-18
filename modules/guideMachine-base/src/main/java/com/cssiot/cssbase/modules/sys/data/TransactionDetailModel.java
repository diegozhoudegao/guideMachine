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
	
	@ApiModelProperty(value="交易类型(0充值、1提现、2退款)")
	private String transactioType;

	@ApiModelProperty(value="方式(0微信、1支付宝、2现金)")
	private String transactioMode;
	
	@ApiModelProperty(value="交易编号")
	private String transactionNo;

	public TransactionDetailModel(TransactionDetail transactionDetail) {
		super();
		this.transactionDetailId = transactionDetail.getId();
		this.visitorsId = transactionDetail.getVisitorsId();
		this.transactioAmount = transactionDetail.getTransactioAmount();
		this.transactioType = transactionDetail.getTransactioType();
		this.transactioMode = transactionDetail.getTransactioMode();
		this.transactionNo=transactionDetail.getTransactionNo();
	}

}