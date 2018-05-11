package com.wu.wechatpay.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wu.wechatpay.rest.entity.WechatRest;
import com.wu.wechatpay.rest.service.WechatRestService;

@RestController
public class WechatRestController {

	@Autowired
	private WechatRestService wechatRestService;

	@RequestMapping(value = "/wechatRest", method = RequestMethod.GET)
	public ResponseEntity<Object> getStudent() {
		WechatRest rest = this.wechatRestService.getTestRest();
		return new ResponseEntity<Object>(rest, HttpStatus.OK);
	}
}
