package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.entity.Member;
import com.dzspring.app.entity.Order;
import com.dzspring.app.service.impl.CartService;
import com.dzspring.app.service.impl.OrderSearchService;
import com.dzspring.app.service.impl.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;
	private final OrderSearchService orderSearchService;
	private final CartService cartService;

	@Autowired
	public OrderController(OrderService orderService, OrderSearchService orderSearchService, CartService cartService) {
		this.orderService = orderService;
		this.orderSearchService = orderSearchService;
		this.cartService = cartService;
	}

	@RequestMapping("/goodsViewTo")
	public ResponseEntity<ResponseMessage> insertSessionAtGodosView(@RequestBody Cart cartItem, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		@SuppressWarnings("unchecked")
		List<Cart> cart = (List<Cart>) session.getAttribute("cart");
		if (null == cart) {
			cart = new ArrayList<Cart>();
			session.setAttribute("cart", cart);
		}
		cartItem.setMemberId(member.getId());
		cartService.insert(cartItem);
		cart.add(cartItem);
		ResponseMessage message = new ResponseMessage();
		message.setUrl(ResponseMessage.path("form/orderInsertForm"));
		message.setData(true);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping("/cartTo")
	public ResponseEntity<ResponseMessage> insertSessionAtCart(@RequestBody Map<String, Object> orderItems, HttpServletRequest request) {
		HttpSession session = request.getSession();
		@SuppressWarnings("unchecked")
		List<Cart> cart = (List<Cart>) session.getAttribute("cart");
		if (null == cart) {
			cart = new ArrayList<Cart>();
			session.setAttribute("cart", cart);
		}

		@SuppressWarnings("unchecked")
		List<Integer> cartIds = ((List<String>) orderItems.get("list"))
								.stream()
								.map(Integer::parseInt)
								.collect(Collectors.toList());
		cartService.findByIdList(cartIds)
			.stream()
			.forEach(cart::add);
		ResponseMessage message = new ResponseMessage();
		message.setUrl(ResponseMessage.path("form/orderInsertForm"));
		message.setData(true);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listOrder(@RequestParam Map<String, String> map) {
		ResponseMessage message = new ResponseMessage();
		List<Order> data = orderSearchService.search(map);
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insertOrder(@RequestBody HashMap<String, Object> map, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		Order order = Order.builder()
						.memberId(member.getId())
						.receiverName((String) map.get("receiverName"))
						.receiverPhone((String) map.get("receiverPhone"))
						.address((String) map.get("address"))
						.payMethod((String) map.get("payMethod"))
						.build();

		@SuppressWarnings("unchecked")
		List<Integer> cartIds = (List<Integer>) map.get("list");

		boolean result = orderService.order(order, cartIds);
		
		data.put("result", result);
		if (result) {
			message.setUrl(ResponseMessage.path(""));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateOrder(@RequestBody Order order) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = false;
//		boolean result = orderService.update(order);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path(""));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteOrder(@PathVariable int id) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = orderService.delete(id);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path(""));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}
