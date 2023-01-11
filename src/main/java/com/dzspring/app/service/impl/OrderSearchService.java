package com.dzspring.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Order;
import com.dzspring.app.repository.OrderRepository;
import com.dzspring.app.service.Command;

@Service
public class OrderSearchService {
	
	private final OrderRepository orderRepository;
	private final Map<String, Method> commandMap;
	
	@Autowired
	public OrderSearchService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
		commandMap = new ConcurrentHashMap<>();
		Arrays.asList(getClass().getDeclaredMethods()).stream()
			.filter(method -> method.getDeclaredAnnotation(Command.class) != null)
			.forEach(method -> {
				Command command = method.getDeclaredAnnotation(Command.class);
				commandMap.put(command.value(), method);
			});
	}
	
	@Command("all")
	public List<Order> allSearch(Map<String, String> map) {
		return orderRepository.findLimit10(map);
	}
	
	@Command("orderId")
	public List<Order> orderIdSearch(Map<String, String> map) {
		int id = Integer.parseInt(map.get("id"));
		return Arrays.asList(orderRepository.findOneById(id));
	}
	
	@Command("memberId")
	public List<Order> memberIdSearch(Map<String, String> map) {
		return orderRepository.findByMemberIdLimit10(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> search(Map<String, String> map) {
		String type = map.get("type");
		if (!commandMap.containsKey(type)) throw new UnsupportedOperationException();
		try {
			return (List<Order>) commandMap.get(type).invoke(this, map);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
