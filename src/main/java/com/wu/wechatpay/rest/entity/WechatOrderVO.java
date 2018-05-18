package com.wu.wechatpay.rest.entity;

import org.springframework.beans.factory.annotation.Value;

public class WechatOrderVO {

	/**
	 * 公众账号ID
	 *
	 * 微信支付分配的公众账号ID（企业号corpid即为此appId）
	 * wxd678efh567hg6787
	 * 必填
	 */
	@Value("${wechat.appid}")
	private String appid;

	/**
	 * 商户号
	 *
	 * 微信支付分配的商户号
	 * 1230000109
	 * 必填
	 */
	@Value("${wechat.mch_id}")
	private String mch_id;

	/**
	 * 签名
	 * 通过签名算法计算得出的签名值，详见签名生成算法
	 * C380BEC2BFD727A4B6845133519F3AD6
	 * 必填
	 */
	private String sign;

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
	 * 终端IP
	 * APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
	 * 123.12.12.123
	 * 必填
	 */
	private String spbill_create_ip;

	/**
	 * 随机字符串
	 *
	 * 随机字符串，长度要求在32位以内。推荐随机数生成算法
	 * 5K8264ILTKCH16CQ2502SI8ZNMTM67VS
	 * 必填
	 */
	private String nonce_str;

	/**
	 * 通知地址
	 * 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
	 * http://www.weixin.qq.com/wxpay/pay.php
	 * 必填
	 */
	@Value("${wechat.notify_url}")
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
	private String trade_type;

	/**
	 * 设备号
	 *
	 * 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
	 * 013467007045764
	 * 非必填
	 */
	private String device_info;

	/**
	 * 签名类型
	 * 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
	 * MD5
	 * 非必填
	 */
	private String sign_type;

	/**
	 * 商品详情
	 * 商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传
	 * 详见https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_102&index=2
	 * 非必填
	 */
	private String detail;

	/**
	 * 附加数据
	 * 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
	 * 非必填
	 */
	private String attach;

	/**
	 * 标价币种
	 * 符合ISO 4217标准的三位字母代码，默认人民币：CNY
	 * 详细列表请参见货币类型https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=4_2
	 * 非必填
	 */
	private String fee_type;

	/**
	 * 交易起始时间
	 * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
	 */
	private String time_start;

	/**
	 * 交易结束时间
	 * 格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。订单失效时间是针对订单号而言的，
	 * 由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，
	 * 所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则
	 * 20091227091010
	 * 非必填
	 */
	private String time_expire;

	/**
	 * 订单优惠标记
	 * 订单优惠标记，使用代金券或立减优惠功能时需要的参数
	 * 说明详见代金券或立减优惠https://pay.weixin.qq.com/wiki/doc/api/tools/sp_coupon.php?chapter=12_1
	 * WXG
	 * 非必填
	 */
	private String goods_tag;

	/**
	 * 商品ID
	 * trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
	 * 12235413214070356458058
	 * 非必填
	 */
	private String product_id;

	/**
	 * 指定支付方式
	 * 上传此参数no_credit--可限制用户不能使用信用卡支付
	 * no_credit
	 * 非必填
	 */
	private String limit_pay;

	/**
	 * 用户标识
	 * trade_type=JSAPI时（即公众号支付），此参数必传，此参数为微信用户在商户对应appid下的唯一标识。openid如何获取
	 * 可参考【获取openid】。企业号请使用【企业号OAuth2.0接口】获取企业号内成员userid，再调用【企业号userid转openid接口】进行转换
	 * oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
	 * 非必填
	 */
	private String openid;

	/**
	 * 场景信息
	 * 该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据
	 * 对象格式为{"store_info":{"id": "门店ID","name": "名称","area_code": "编码","address": "地址" }}
	 * 非必填
	 */
	private WechatSceneInfo wechatSceneInfo;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

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

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getDevice_info() {
		return device_info;
	}

	public void setDevice_info(String device_info) {
		this.device_info = device_info;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getFee_type() {
		return fee_type;
	}

	public void setFee_type(String fee_type) {
		this.fee_type = fee_type;
	}

	public String getTime_start() {
		return time_start;
	}

	public void setTime_start(String time_start) {
		this.time_start = time_start;
	}

	public String getTime_expire() {
		return time_expire;
	}

	public void setTime_expire(String time_expire) {
		this.time_expire = time_expire;
	}

	public String getGoods_tag() {
		return goods_tag;
	}

	public void setGoods_tag(String goods_tag) {
		this.goods_tag = goods_tag;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getLimit_pay() {
		return limit_pay;
	}

	public void setLimit_pay(String limit_pay) {
		this.limit_pay = limit_pay;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public WechatSceneInfo getWechatSceneInfo() {
		return wechatSceneInfo;
	}

	public void setWechatSceneInfo(WechatSceneInfo wechatSceneInfo) {
		this.wechatSceneInfo = wechatSceneInfo;
	}
}
