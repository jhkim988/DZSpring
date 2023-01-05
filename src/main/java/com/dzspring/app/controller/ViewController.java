package com.dzspring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dzspring.app.service.GoodsService;

@Controller
@RequestMapping("/view")
public class ViewController {

	private final GoodsService goodsService;
	
	@Autowired
	public ViewController(GoodsService goodsService) {
		this.goodsService = goodsService;
	}
	
	@RequestMapping("/member")
	public String viewMember() {
		return "/view/member";
	}
	
	@RequestMapping("/goods/{id}")
	public ModelAndView viewGoods(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("/view/goods");
		mav.addObject("goods", goodsService.findOneById(id));
		return mav;
	}
}
