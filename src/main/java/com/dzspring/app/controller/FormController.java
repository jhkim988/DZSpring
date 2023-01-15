package com.dzspring.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dzspring.app.entity.Cart;
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
	
	@RequestMapping("/admin/updateMemberForm/{id}")
	public String adminUpdateMemberForm(@PathVariable String id, HttpServletRequest request) {
		Optional<Member> member = memberService.findOneById(id);
		member.ifPresent(mem -> request.setAttribute("manage_member", mem));
		return "/form/admin/updateMemberForm";
	}
	
	@RequestMapping("/admin/insertGoodsForm")
	public String insertGoodsForm() {
		return "/form/admin/insertGoodsForm";
	}
	
	@RequestMapping("/admin/updateGoodsForm/{id}")
	public ModelAndView updateGoodsForm(@PathVariable int id) {
		ModelAndView mav = new ModelAndView("/form/admin/updateGoodsForm");
		mav.addObject("goods", goodsService.findOneById(id).get());
		return mav;
	}
	
	@RequestMapping("/admin/insertOrderForm")
	public ModelAndView insertOrderForm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/form/admin/insertOrderForm");
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Cart> cart = (List<Cart>) session.getAttribute("cart");
		List<Integer> goodsIds = cart.stream()
									.map(cartItem -> cartItem.getGoodsId())
									.sorted(Integer::compare)
									.collect(Collectors.toList());
		mav.addObject("goods", goodsService.toGoodsList(goodsIds));
		mav.addObject("cart", cart);
		session.removeAttribute("cart");
		return mav;
	}
	
	@RequestMapping("/admin/updateOrderForm/{id}")
	public ModelAndView updateOrderForm(@PathVariable int id) {
		ModelAndView mav= new ModelAndView("/form/admin/updateOrderForm");
		mav.addObject("order", orderService.findOneById(id).get());
		mav.addObject("orderItems", orderService.getGoodsByOrderId(id));
		return mav;
	}
}
