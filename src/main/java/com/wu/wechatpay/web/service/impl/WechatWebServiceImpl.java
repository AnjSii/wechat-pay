package com.wu.wechatpay.web.service.impl;

import java.awt.image.BufferedImage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wu.wechatpay.rest.entity.WechatUnifiedOrderVO;
import com.wu.wechatpay.utils.HttpClientUtil;
import com.wu.wechatpay.utils.Log4jUtil;
import com.wu.wechatpay.utils.QRCodeEncoderHandler;
import com.wu.wechatpay.utils.RandomUtil;
import com.wu.wechatpay.utils.SignUtil;
import com.wu.wechatpay.utils.XMLUtil;
import com.wu.wechatpay.web.service.WechatWebService;

@Service
public class WechatWebServiceImpl implements WechatWebService {

	private static String nonce_str = RandomUtil.getRandomByLength(32);

	private static String out_trade_no = RandomUtil.getRandomByLength(32);

	private static String product_id = RandomUtil.getRandomByLength(32);

	@Value("${wechat.appid}")
	private String appid;

	@Value("${wechat.mch_id}")
	private String mch_id;

	@Value("${wechat.notify_url}")
	private String notify_url;

	@Value("${wechat.key}")
	private String key;

	@Value("${wechat.unifiedorder}")
	private String unifiedorder_url;

	@Value("${wechat.orderquery}")
	private String orderquery_url;

	@Override
	public Map<String, String> wechatQRpay() throws UnknownHostException {
		Map<String, String> resultMap = new HashMap<>();
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", appid); // appid
		paramMap.put("mch_id", mch_id); // 商户号
		paramMap.put("nonce_str", nonce_str); // 随机数
		paramMap.put("body", "采料网-测试商品"); // 描述
		paramMap.put("out_trade_no", out_trade_no); // 商户后台的贸易单号
		paramMap.put("total_fee", 1); // 金额必须为整数 单位为分
		paramMap.put("spbill_create_ip", InetAddress.getLocalHost().getHostAddress()); // 本机的Ip
		paramMap.put("notify_url", notify_url); // 支付成功后，回调地址
		paramMap.put("trade_type", WechatUnifiedOrderVO.TradeType.NATIVE.toString()); // 交易类型
		paramMap.put("product_id", product_id); // 商品id NATIVE时必填
		String sign = SignUtil.getSign(paramMap, key); //生成前面需要所有参数与商户key
		paramMap.put("sign", sign); // 根据微信签名规则，生成签名

		String paramXml = XMLUtil.mapToXml(paramMap); //转换成xml
		String resXml = HttpClientUtil.postData(unifiedorder_url, paramXml);
		Log4jUtil.info("获取二维码: " + resXml);

		if (resXml != null) {
			try {
				Document document = DocumentHelper.parseText(resXml);
				Element root = document.getRootElement();

				if ("SUCCESS".equals(root.element("return_code").getText())) { //成功调用
					if ("SUCCESS".equals(root.element("result_code").getText())) {
						QRCodeEncoderHandler handler = new QRCodeEncoderHandler();
						//创建二维码
						try {
							BufferedImage bufferedImage = handler.createImage(root.element("code_url").getText(),
									null, false);
							String imgBase64 = handler.encodeToBase64(bufferedImage, QRCodeEncoderHandler.FORMAT_NAME);
							resultMap.put("QRCode", imgBase64);
						} catch (Exception e) {
							resultMap.put("err_code", "SYSTEMERROR");
							resultMap.put("err_code_des", "系统异常");
							e.printStackTrace();
						}
					} else {
						resultMap.put("err_code", root.element("err_code").getText());
						resultMap.put("err_code_des", root.element("err_code_des").getText());
					}
				} else if ("FAIL".equals(root.element("return_code").getText())) {
					Log4jUtil.debug("支付出错.错误信息：" + root.element("return_msg").getText());
					resultMap.put("return_msg", root.element("return_msg").getText());
				} else {
					resultMap.put("err_code", "SYSTEMERROR");
					resultMap.put("err_code_des", "系统异常");
				}
			} catch (DocumentException e) {
				Log4jUtil.error(e.getMessage());
				resultMap.put("err_code", "SYSTEMERROR");
				resultMap.put("err_code_des", e.getMessage());
				e.printStackTrace();
			}
		} else {
			Log4jUtil.error("调用统一下单接口失败，没有返回参数");
			resultMap.put("err_code", "SYSTEMERROR");
			resultMap.put("err_code_des", "调用统一下单接口失败，没有返回参数");
		}
		return resultMap;
	}

	@Override
	public Map<String, String> orderCheck() {

		Map<String, String> resultMap = new HashMap<>();

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("appid", appid); // appid
		paramMap.put("mch_id", mch_id); // 商户号
		paramMap.put("nonce_str", nonce_str); // 随机数
		paramMap.put("out_trade_no", out_trade_no); // 商户后台的贸易单号
		String sign = SignUtil.getSign(paramMap, key); //生成前面需要所有参数与商户key
		paramMap.put("sign", sign); // 根据微信签名规则，生成签名

		String paramXml = XMLUtil.mapToXml(paramMap); //转换成xml
		String resXml = HttpClientUtil.postData(orderquery_url, paramXml);
		Log4jUtil.info("查询订单状态: " + resXml);

		if (resXml != null) {
			try {
				Document document = DocumentHelper.parseText(resXml);
				Element root = document.getRootElement();
				if ("SUCCESS".equals(root.element("return_code").getText())) { //成功调用
					if ("SUCCESS".equals(root.element("result_code").getText())) {
						resultMap.put("trade_state", root.element("trade_state").getText());
						switch (root.element("trade_state").getText()) {
							case "SUCCESS": //支付成功
								// TODO 业务逻辑的处理
								resultMap.put("trade_des", "支付成功");
								break;
							case "REFUND": //转入退款
								resultMap.put("trade_des", "转入退款");
								// TODO 业务逻辑的处理
								break;
							case "NOTPAY": //未支付
								resultMap.put("trade_des", "未支付");
								// TODO 业务逻辑的处理
								break;
							case "CLOSED": //已关闭
								resultMap.put("trade_des", "已关闭");
								// TODO 业务逻辑的处理
								break;
							case "REVOKED": //已撤销
								resultMap.put("trade_des", "已撤销");
								// TODO 业务逻辑的处理
								break;
							case "USERPAYING": //用户支付中
								resultMap.put("trade_des", "用户支付中");
								// TODO 业务逻辑的处理
								break;
							case "PAYERROR": //支付失败
								resultMap.put("trade_des", "支付失败");
								// TODO 业务逻辑的处理
								break;
						}
					} else {
						resultMap.put("err_code", root.element("err_code").getText());
						resultMap.put("err_code_des", root.element("err_code_des").getText());
					}
				} else if ("FAIL".equals(root.element("return_code").getText())) {
					Log4jUtil.debug("查询订单出错，错误信息：" + root.element("return_msg").getText());
					resultMap.put("return_msg", root.element("return_msg").getText());
				} else {
					resultMap.put("err_code", "SYSTEMERROR");
					resultMap.put("err_code_des", "查询订单出错，系统异常");
				}
			} catch (DocumentException e) {
				Log4jUtil.error(e.getMessage());
				resultMap.put("err_code", "SYSTEMERROR");
				resultMap.put("err_code_des", e.getMessage());
				e.printStackTrace();
			}
		} else {
			Log4jUtil.error("调用统一下单接口失败，没有返回参数");
			resultMap.put("err_code", "SYSTEMERROR");
			resultMap.put("err_code_des", "调用查询订单接口失败，没有返回参数");
		}
		return resultMap;
	}
}
