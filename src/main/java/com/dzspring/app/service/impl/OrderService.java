package com.dzspring.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dzspring.app.entity.Order;
import com.dzspring.app.entity.OrderItem;
import com.dzspring.app.repository.OrderItemRepository;
import com.dzspring.app.repository.OrderRepository;

@Service
public class OrderService {

	private final OrderRepository orderRepository;
	private final OrderItemRepository orderItemRepository;
	private final CartService cartService;

	@Autowired
	public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository,
			CartService cartService) {
		this.orderRepository = orderRepository;
		this.orderItemRepository = orderItemRepository;
		this.cartService = cartService;
	}

	public List<Order> list(Map<String, Object> map) {
		return orderRepository.list(map);
	}

	public boolean delete(int id) {
		return 1 == orderRepository.delete(id);
	}

	public Optional<Order> findOneById(int id) {
		return Optional.ofNullable(orderRepository.findOneById(id));
	}

	public List<OrderItem> findOrderItemByOrderId(int orderId) {
		return orderItemRepository.findItemsByOrderId(orderId);
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public boolean order(Order order, List<Integer> cartIds) {
		int orderId = orderRepository.insert(order);
		return orderItemRepository.insertItems(
				cartService
					.findByIdList(cartIds).stream()
					.map(cartItem -> OrderItem.builder()
						.orderId(orderId)
						.goodsId(cartItem.getGoodsId())
						.quantity(cartItem.getQuantity())
						.build())
					.collect(Collectors.toList())) > 0;
	}
}
