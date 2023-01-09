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

import com.dzspring.app.entity.Order;
import com.dzspring.app.service.impl.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> listOrder(Map<String, Object> map) {
		ResponseMessage message = new ResponseMessage();
		message.setData(orderService.list(map));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insertOrder(@RequestBody Order order) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = orderService.insert(order);
		data.put("result", result);
		if (result) {
			data.put("url", ResponseMessage.path(""));
		}
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateOrder(@RequestBody Order order) {
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		boolean result = orderService.update(order);
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
