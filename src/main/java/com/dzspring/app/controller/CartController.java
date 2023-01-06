package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.service.impl.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	private final CartService cartService;
	
	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listCart(Map<String, Object> map) {
		ResponseMessage message = new ResponseMessage();
		message.setData(cartService.list(map));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insertOrder(@RequestBody Cart cart) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = cartService.insert(cart);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path("/viw/cart"));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateOrder(@RequestBody Cart cart) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = cartService.update(cart);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path("/view/cart"));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> deleteOrder(@PathVariable int id) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = cartService.delete(id); // who, what, where, how
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path("/view/cart"));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}
