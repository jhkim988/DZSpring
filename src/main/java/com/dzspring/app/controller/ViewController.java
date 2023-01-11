package com.dzspring.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dzspring.app.service.GoodsService;
import com.dzspring.app.service.impl.OrderService;

@Controller
@RequestMapping("/view")
public class ViewController {

	private final GoodsService goodsService;
	private final OrderService orderService;
	
	@Autowired
	public ViewController(GoodsService goodsService, OrderService orderService) {
		this.goodsService = goodsService;
		this.orderService = orderService;
	}
	
	@RequestMapping("/member")
	public String viewMember() {
		return "/view/member";
	}
	
	@RequestMapping("/goods/{id}")
	public ModelAndView viewGoods(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("403"); // TODO: 실패 화면
		goodsService.findOneById(id).ifPresent(goods -> {
			mav.addObject("goods", goods);
			mav.setViewName("/view/goods");
		});
		return mav;
	}
	
	@RequestMapping("/orderList")
	public String orderList() {
		return "/view/orderList";
	}
	
	@RequestMapping("/order/{id}")
	public ModelAndView viewOrder(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("/view/order");
		mav.addObject("order", orderService.findOneById(id).get());
		mav.addObject("orderItems", orderService.getGoodsByOrderId(id));
		return mav;
	}
	
	@RequestMapping("/cart")
	public String cart() {
		return "/view/cart";
	}
}
