package com.wu.wechatpay.rest.entity;

public class WechatUnifiedOrderVO {

	/**
	 * 商品描述
	 * 商品简单描述，该字段请按照规范传递
	 * 具体请见https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
	 * 浏览器打开的网站主页title名 -商品概述
	 * 必填
	 */
	private String body;

	/**
	 * 标价金额
	 * 订单总金额，单位为分，详见支付金额
	 * 88
	 * 必填
	 */
	private Integer total_fee;

	/**
	 * 商户订单号
	 * 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|* 且在同一个商户号下唯一
	 * 详见https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
	 * 20150806125346
	 * 必填
	 */
	private String out_trade_no;

	/**
	 * 通知地址
	 * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	 * http://www.weixin.qq.com/wxpay/pay.php
	 * 必填
	 */
	private String notify_url;

	/**
	 * 交易类型
	 * JSAPI 公众号支付
	 * NATIVE 扫码支付
	 * APP APP支付
	 * 说明详见https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
	 * JSAPI
	 * 必填
	 */
	private TradeType trade_type;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(Integer total_fee) {
		this.total_fee = total_fee;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public TradeType getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(TradeType trade_type) {
		this.trade_type = trade_type;
	}

	public enum TradeType {
		/**
		 * 公众号支付
		 */
		JSAPI,

		/**
		 * 扫码支付
		 */
		NATIVE,

		/**
		 * APP支付
		 */
		APP
	}
}
