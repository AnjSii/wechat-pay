package com.wu.wechatpay.rest.service.impl;

import org.springframework.stereotype.Service;

import com.wu.wechatpay.rest.entity.WechatRest;
import com.wu.wechatpay.rest.service.WechatRestService;

@Service
public class WechatRestServiceIml implements WechatRestService {
	@Override
	public WechatRest getTestRest() {

		WechatRest wechatRest = new WechatRest();
		wechatRest.setId(1L);
		wechatRest.setValue("this is WechatRest");

		return wechatRest;
	}
}
