package com.dzspring.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Order;
import com.dzspring.app.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	
	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}
	
	public List<Order> list(Map<String, Object> map) {
		return orderRepository.list(map);
	}
	
	public boolean insert(Order order) {
		return 1 == orderRepository.insert(order);
	}
	
	public boolean update(Order order) {
		return 1 == orderRepository.update(order);
	}
	
	public boolean delete(int id) {
		return 1 == orderRepository.delete(id);
	}
	
	public Optional<Order> findOneById(int id) {
		return Optional.ofNullable(orderRepository.findOneById(id));
	}
	
}
