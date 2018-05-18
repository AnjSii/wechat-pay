package com.wu.wechatpay.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wu.wechatpay.utils.CommUtil;
import com.wu.wechatpay.web.service.WechatWebService;

@Controller
public class WechatpayController {

	@Autowired
	private WechatWebService wechatWebService;

	@RequestMapping({ "/wechatpay_toPay.htm" })
	public String toPay(HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("webPath", CommUtil.getURL(request));
		return "wechatpay_toPay";
	}

	@RequestMapping({ "/wechatpay_QRCode.htm" })
	public void getRQCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");

		Map<String, String> resultMap = this.wechatWebService.wechatQRpay();
		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(resultMap, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/wechatpay_OrderCheck" })
	public void wechatpay_OrderCheck(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");

		Map<String, String> resultMap = this.wechatWebService.orderCheck();

		try {
			PrintWriter writer = response.getWriter();
			writer.print(Json.toJson(resultMap, JsonFormat.compact()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping({ "/wechatpay_notify.htm" }) //由于没有部署到外网的服务器，所以只能使用内网的轮询方法查询是否支付成功
	public String wechatpay_notify(HttpServletRequest request, HttpServletResponse response, Model model, String message) {
		model.addAttribute("message", message);
		return "wechatpay_notify";
	}
}
