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

	/**
	 * Order Table 의 허용된 컬럼(receiverName, receiverPhone, address, payMethod, status)만 수정합니다.
	 * @param order
	 * @return db 쿼리 수행 성공 여부
	 */
	public boolean update(Order order) {
		return 1 == orderRepository.updateReceiverInfo(order);
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
		orderRepository.insert(order);
		boolean result = orderItemRepository.insertItems(cartService
					.findByIdList(cartIds).stream()
					.map(cartItem -> OrderItem.builder()
						.orderId(order.getId())
						.goodsId(cartItem.getGoodsId())
						.quantity(cartItem.getQuantity())
						.build())
					.collect(Collectors.toList())
					) > 0;
		order.setTotalPrice(cartService.getTotalPrice(cartIds));
		cartService.delete(cartIds);
		orderRepository.update(order);
		return result;
	}
	
	public List<Map<String, Object>> getGoodsByOrderId(int id) {
		return orderItemRepository.getGoodsByOrderId(id);
	}
}
