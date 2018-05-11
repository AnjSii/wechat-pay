package com.wu.wechatpay.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {
	@RequestMapping({ "/welcome.htm" })
	public String welcome(Model model) {
		model.addAttribute("welcome", "welcome");
		return "welcome";
	}
}
