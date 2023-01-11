package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.entity.Member;
import com.dzspring.app.entity.Order;
import com.dzspring.app.service.MemberService;
import com.dzspring.app.service.impl.CartService;
import com.dzspring.app.service.impl.OrderService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private final MemberService memberService;
	private final OrderService orderService;
	private final CartService cartService;
	
	@Autowired
	public TestController(MemberService memberService, OrderService orderService, CartService cartService) {
		this.memberService = memberService;
		this.orderService = orderService;
		this.cartService = cartService;
	}
	
	@RequestMapping("/generateMember")
	public ResponseEntity<ResponseMessage> generateMember() {
		for (int i = 0; i < 100; i++) {
			memberService.register(Member.builder()
					.id("user"+i)
					.pwd("pwd")
					.name("자바좋아"+i)
					.sex("M")
					.email("java" + i + "@naver.com")
					.phone("0100000000"+i)
					.birth(Date.valueOf("1900-01-01")
)					.build());
		}
		return new ResponseEntity<>(null, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping("/generateOrder")
	public ResponseEntity<ResponseMessage> generateOrder() {
		ThreadLocalRandom rand = ThreadLocalRandom.current();
		for (int i = 0; i < 100; i++) {
			List<Integer> list = new ArrayList<>();
			for (int j = 0; j < 3; j++) {
				Cart cart = Cart.builder()
								.memberId("user"+i)
								.goodsId(337+j)
								.quantity(rand.nextInt(5))
								.build();
				cartService.insert(cart);
				list.add(cart.getId());
			}
			orderService.order(Order.builder()
					.memberId("user" + i)
					.receiverName("자바좋아"+i)
					.receiverPhone("0100000000"+i)
					.address("혜화역 4번출구")
					.payMethod("외상")
					.status("배송 준비중")
					.build(), list);
		}
		return new ResponseEntity<>(null, getJSONHeader(), HttpStatus.OK);
	}
}
