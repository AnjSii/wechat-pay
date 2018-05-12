package com.wu.wechatpay.rest.service;

import com.wu.wechatpay.rest.entity.WechatOrderVO;

public interface WechatRestService {

	String unifiedOrder(WechatOrderVO wechatOrderVO, String remoteAddr);
}
