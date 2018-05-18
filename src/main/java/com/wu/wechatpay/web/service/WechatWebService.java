package com.wu.wechatpay.web.service;

import java.net.UnknownHostException;
import java.util.Map;

public interface WechatWebService {
	Map<String, String> wechatQRpay() throws UnknownHostException;

	Map<String, String> orderCheck();
}
