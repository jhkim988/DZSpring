package com.dzspring.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.dzspring.app.service.impl.GoodsSearchService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final GoodsSearchService goodsSearchService;
	
	@Autowired
	public HomeController(GoodsSearchService goodsSearchService) {
		this.goodsSearchService = goodsSearchService;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView mav = new ModelAndView("home");
		Map<String, Object> map = new HashMap<>();
		map.put("value", "bestseller");
		mav.addObject("goods", goodsSearchService.statusCodeSearch(map));
		return mav;
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
}
