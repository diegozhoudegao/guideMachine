package com.cssiot.cssbase.modules.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.cssiot.cssbase.modules.sys.data.TransactionDetailModel;
import com.cssiot.cssutil.common.entity.CommonFields;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 交易明细表
 * @author
 *	2018-10-05 athena 创建
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@Table(name = "t_sys_transactionDetail")
public class TransactionDetail extends CommonFields{
	
	@ApiModelProperty(value="游客id")
	@Column(name="visitorsId")
	private String visitorsId;
	
	@ApiModelProperty(value="交易金额")
	@Column(name="transactioAmount")
	private Integer transactioAmount;
	
	@ApiModelProperty(value="交易类型(充值、提现、退款)")
	@Column(name="transactioType")
	private String transactioType;

	@ApiModelProperty(value="方式(微信、支付宝)")
	@Column(name="transactioMode")
	private String transactioMode;
	
	public TransactionDetail(TransactionDetailModel transactionDetail) {
		super();
		this.visitorsId = transactionDetail.getVisitorsId();
		this.transactioAmount = transactionDetail.getTransactioAmount();
		this.transactioType = transactionDetail.getTransactioType();
		this.transactioMode = transactionDetail.getTransactioMode();
	}
}