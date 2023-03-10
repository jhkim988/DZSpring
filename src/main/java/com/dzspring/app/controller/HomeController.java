package com.dzspring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value = "/admin/member")
	public String adminMember() {
		return "/admin/member";
	}
	
	@RequestMapping(value = "/admin/page")
	public String adminPage() {
		return "/admin/page";
	}
	
	@RequestMapping(value = "/admin/goods")
	public String adminGoods() {
		return "/admin/goods";
	}
	
	@RequestMapping(value="/admin/order")
	public String adminOrder() {
		return "/admin/order";
	}
	
	@RequestMapping(value="/admin/qna")
	public String adminQna() {
		return "/admin/qna";
	}
}
