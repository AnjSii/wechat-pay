package com.wu.wechatpay.rest.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.wu.wechatpay.rest.entity.WechatOrderVO;
import com.wu.wechatpay.rest.service.WechatRestService;
import com.wu.wechatpay.utils.WechatUtil;

@Service
public class WechatRestServiceIml implements WechatRestService {

	@Value("${wechat.appid}")
	private String appid;

	@Value("${wechat.mch_id}")
	private String mch_id;

	@Value("${wechat.notify_url}")
	private String notify_url;

	@Override
	public String unifiedOrder(WechatOrderVO wechatOrderVO, String remoteAddr) {

		String xml = "<xml>\n" +
				"<appid>\n" + appid + "</appid>\n" +
				"<mch_id>\n" + mch_id +"</mch_id>\n" +
				"<body>\n" + wechatOrderVO.getBody() + "</body>\n" +
				"<nonce_str>\n" + WechatUtil.random(32) + "</nonce_str>\n" +
				"<notify_url>\n" + notify_url + "</notify_url>\n" +
				"<out_trade_no>\n" + wechatOrderVO.getOut_trade_no() + "</out_trade_no>\n" +
				"<spbill_create_ip>\n" + remoteAddr + "</spbill_create_ip>\n" +
				"<total_fee>\n" + wechatOrderVO.getTotal_fee() + "</total_fee>\n" +
				"<trade_type>\n" + wechatOrderVO.getTrade_type() + "</trade_type>\n" +
				"<sign>0CB01533B8C1EF103065174F50BCA001</sign>\n" +
				"</xml>";

		return null;
	}
}
