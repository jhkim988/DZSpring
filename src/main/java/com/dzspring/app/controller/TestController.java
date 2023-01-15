package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.entity.Member;
import com.dzspring.app.entity.Order;
import com.dzspring.app.service.GoodsService;
import com.dzspring.app.service.MemberService;
import com.dzspring.app.service.impl.CartService;
import com.dzspring.app.service.impl.OrderService;

@RestController
@RequestMapping("/test")
public class TestController {
	
	private final MemberService memberService;
	private final GoodsService goodsService;
	private final OrderService orderService;
	private final CartService cartService;
	
	@Autowired
	public TestController(MemberService memberService, GoodsService goodsService, OrderService orderService, CartService cartService) {
		this.memberService = memberService;
		this.goodsService = goodsService;
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
					.phone("010100000"+i)
					.birth(Date.valueOf("1900-01-01"))
					.build());
		}
		return new ResponseEntity<>(null, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping("/generateGoods")
	public ResponseEntity<ResponseMessage> generateGodos() {
		for (int i = 0; i < 100; i++) {
			ThreadLocalRandom rand = ThreadLocalRandom.current();
			Map<String, Object> goodsInfo = new HashMap<>();
			goodsInfo.put("category", "카테고리" + rand.nextInt(10));;
			goodsInfo.put("title", "제목" + i);
			goodsInfo.put("author", "작가" + i);
			goodsInfo.put("publisher", "KOSA" + rand.nextInt(10));
			goodsInfo.put("price", rand.nextInt(10000, 30000) + "");
			goodsInfo.put("publishedAt", Date.valueOf(LocalDate.now()).toString());
			goodsInfo.put("totalPage", "100");
			goodsInfo.put("statusCode", "bestseller");
			goodsInfo.put("intro", "사실은 더미데이터");
			goodsInfo.put("authorIntro", "좋은 책입니다.");
			goodsInfo.put("publisherIntro", "일단 사세요");
			goodsInfo.put("recommendation", "이 책을 추천합니다.");
			goodsInfo.put("img", Arrays.asList("default"));
			goodsService.insert(goodsInfo);
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
								.goodsId(rand.nextInt(3)+50)
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
