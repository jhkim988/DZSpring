package com.dzspring.app.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.GoodsService;
import com.dzspring.app.service.MemberService;
import com.dzspring.app.service.impl.OrderService;

@Controller
@RequestMapping("/form")
public class FormController {

	private final MemberService memberService;
	private final GoodsService goodsService;
	private final OrderService orderService;
	@Autowired
	public FormController(MemberService memberService, GoodsService goodsService, OrderService orderService) {
		this.memberService = memberService;
		this.goodsService = goodsService;
		this.orderService = orderService;
	}
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "/form/loginForm";
	}
	
	@RequestMapping("/findIdForm")
	public String findId() {
		return "/form/findIdForm";
	}
	
	@RequestMapping("/findPwdForm")
	public String findPwd() {
		return "/form/findPwdForm";
	}
	
	@RequestMapping("/registerForm")
	public String registerForm() {
		return "/form/registerForm";
	}
	
	@RequestMapping("/deleteMemberForm")
	public String deleteMember() {
		return "/form/deleteMemberForm";
	}
	
	@RequestMapping("/updateMemberForm")
	public String updateMember() {
		return "/form/updateMemberForm";
	}
	
	@RequestMapping("/adminUpdateMemberForm/{id}")
	public String adminUpdateMemberForm(@PathVariable String id, HttpServletRequest request) {
		Optional<Member> member = memberService.findOneById(id);
		member.ifPresent(mem -> request.setAttribute("manage_member", mem));
		return "/form/adminUpdateMemberForm";
	}
	
	@RequestMapping("/goodsInsertForm")
	public String goodsInsertForm() {
		return "/form/goodsInsertForm";
	}
	
	@RequestMapping("/goodsUpdateForm/{id}")
	public ModelAndView goodsUpdateForm(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("/form/goodsUpdateForm");
		mav.addObject("goods", goodsService.findOneById(id));
		return mav;
	}
	
	@RequestMapping("/orderInsertForm/{goodsId}")
	public ModelAndView orderInsertForm(@PathVariable int goodsId) {
		ModelAndView mav = new ModelAndView(); // TODO: 실패 시 주소 입력
		goodsService.findOneById(goodsId).ifPresent(goods -> {
			mav.addObject("goods", goods);
			mav.setViewName("/form/orderInsertForm");
		});
		return mav;
	}
	
	@RequestMapping("/orderUpdateForm/{orderId}")
	public ModelAndView orderUpdateForm(@PathVariable int orderId) {
		ModelAndView mav= new ModelAndView(""); // TODO: 실패 시 주소 입력
		orderService.findOneById(orderId).ifPresent(order -> {
			mav.addObject("order", order);
			goodsService.findOneById(order.getGoodsId()).ifPresent(goods -> {
				mav.addObject("goods", goods);
				mav.setViewName("/form/orderUpdateForm");
			});
		});
		return mav;
	}
}
