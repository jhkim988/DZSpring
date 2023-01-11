package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.entity.Goods;
import com.dzspring.app.entity.Member;
import com.dzspring.app.service.GoodsService;
import com.dzspring.app.service.impl.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	private final GoodsService goodsService;
	
	@Autowired
	public CartController(CartService cartService, GoodsService goodsService) {
		this.cartService = cartService;
		this.goodsService = goodsService;
	}
	
	@RequestMapping(value={"/", "/{lastId}"}, method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listCart(@PathVariable Optional<String> lastId, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		map.put("memberId", member.getId());
		ResponseMessage message = new ResponseMessage();
		List<Cart> cartList = cartService.listByMember(map);
		List<Goods> goodsList = goodsService.toGoodsList(cartList.stream().map(Cart::getGoodsId).collect(Collectors.toList()));
		Map<String, Object> data = new HashMap<>();
		data.put("cartList", cartList);
		data.put("goodsList", goodsList);
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insertCart(@RequestBody Cart cart, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		cart.setMemberId(member.getId());
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = cartService.insert(cart);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path("/view/cart"));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteCart(@PathVariable int id) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = cartService.delete(id);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path("/view/cart"));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}
