package com.cssiot.cssbase.modules.wxpay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cssiot.cssbase.modules.wxpay.service.WxPayResultService;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayOrderReverseRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundQueryRequest;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderCloseResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayOrderReverseResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundQueryResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;

import io.swagger.annotations.ApiOperation;

/**
 * 微信支付
 * @author 
 * 	2018-10-14 Diego.zhou 新建
 *
 */
@Service
@Transactional
public class WxPayResultServiceImpl implements WxPayResultService {
	
	private WxPayService wxService;

	@Autowired
	public WxPayResultServiceImpl(WxPayService wxService) {
	   this.wxService = wxService;
	}

	/**
	 * 创建订单，返回支付参数（5个参数和sign）
	 * @param mchId 商户Id
	 * @param mchKey 商户秘钥
	 * @param request
	 * @return
	 * @throws WxPayException
	 */
	public WxPayMpOrderResult doCreateOrder(String mchId,String mchKey,WxPayUnifiedOrderRequest request) throws WxPayException {
		// 获取config,并替换其中的商户信息
		WxPayConfig config = this.wxService.getConfig();
		config.setMchId(mchId);
		config.setMchKey(mchKey);
		return this.wxService.createOrder(request);
	}
	
	/**
	  * 查询订单(详见https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_2)
	  * 该接口提供所有微信支付订单的查询，商户可以通过查询订单接口主动查询订单状态，完成下一步的业务逻辑。
	  * 需要调用查询接口的情况：
	  * ◆ 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知；
	  * ◆ 调用支付接口后，返回系统错误或未知交易状态情况；
	  * ◆ 调用被扫支付API，返回USERPAYING的状态；
	  * ◆ 调用关单或撤销接口API之前，需确认支付状态；
	  * 接口地址：https://api.mch.weixin.qq.com/pay/orderquery
	  * </pre>
	  *
	  * @param transactionId 微信订单号
	  * @param outTradeNo    商户系统内部的订单号，当没提供transactionId时需要传这个。
	  */
	  public WxPayOrderQueryResult queryOrder(String transactionId,String outTradeNo)throws WxPayException {
	    return this.wxService.queryOrder(transactionId, outTradeNo);
	  }
	  
	/**
	  * 微信支付-申请退款
	  * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
	  * 接口链接：https://api.mch.weixin.qq.com/secapi/pay/refund
	  *
	  * @param request 请求对象
	  * @return 退款操作结果
	 */
	 public WxPayRefundResult refund(WxPayRefundRequest request) throws WxPayException {
	   return this.wxService.refund(request);
	 }

	  /**
	   * <pre>
	   * 微信支付-查询退款
	   * 应用场景：
	   *  提交退款申请后，通过调用该接口查询退款状态。退款有一定延时，用零钱支付的退款20分钟内到账，
	   *  银行卡支付的退款3个工作日后重新查询退款状态。
	   * 详见 https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
	   * 接口链接：https://api.mch.weixin.qq.com/pay/refundquery
	   * </pre>
	   * 以下四个参数四选一
	   *
	   * @param transactionId 微信订单号
	   * @param outTradeNo    商户订单号
	   * @param outRefundNo   商户退款单号
	   * @param refundId      微信退款单号
	   * @return 退款信息
	   */
	  public WxPayRefundQueryResult refundQuery(WxPayRefundQueryRequest wxPayRefundQueryRequest) throws WxPayException {
	    return this.wxService.refundQuery(wxPayRefundQueryRequest);
	  }

	  /**
	   * 支付回调通知处理
	   * TODO 此方法需要改造，根据实际需要返回com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse对象
	   */
	  public WxPayOrderNotifyResult parseOrderNotifyResult(@RequestBody String xmlData) throws WxPayException {
	    return this.wxService.parseOrderNotifyResult(xmlData);
	  }

	  /**
	   * 退款回调通知处理
	   * TODO 此方法需要改造，根据实际需要返回com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse对象
	   */
	  public WxPayRefundNotifyResult parseRefundNotifyResult(@RequestBody String xmlData) throws WxPayException {
	    return this.wxService.parseRefundNotifyResult(xmlData);
	  }
	  
	  /**
	   * 关闭订单(暂不使用)
	   * 应用场景
	   * 以下情况需要调用关单接口：
	   * 1. 商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
	   * 2. 系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
	   * 注意：订单生成后不能马上调用关单接口，最短调用时间间隔为5分钟。
	   * 接口地址：https://api.mch.weixin.qq.com/pay/closeorder
	   * 是否需要证书：   不需要。
	   * @param outTradeNo 商户系统内部的订单号
	   */
	  public WxPayOrderCloseResult closeOrder(String outTradeNo) throws WxPayException {
		  return this.wxService.closeOrder(outTradeNo);
	  }
	
	/**
	   * 撤销订单API
	   * 文档地址：https://pay.weixin.qq.com/wiki/doc/api/micropay.php?chapter=9_11&index=3
	   * 应用场景：
	   *  支付交易返回失败或支付系统超时，调用该接口撤销交易。如果此订单用户支付失败，微信支付系统会将此订单关闭；如果用户支付成功，微信支付系统会将此订单资金退还给用户。
	   *  注意：7天以内的交易单可调用撤销，其他正常支付的单如需实现相同功能请调用申请退款API。提交支付交易后调用【查询订单API】，没有明确的支付结果再调用【撤销订单API】。
	   *  调用支付接口后请勿立即调用撤销订单API，建议支付后至少15s后再调用撤销订单接口。
	   *  接口链接 ：https://api.mch.weixin.qq.com/secapi/pay/reverse
	   *  是否需要证书：请求需要双向证书。
	   */
	public WxPayOrderReverseResult reverseOrder(WxPayOrderReverseRequest request) throws WxPayException {
		return this.wxService.reverseOrder(request);
	}
}
