package com.dzspring.app.repository;

import java.util.List;

import com.dzspring.app.entity.OrderItem;

public interface OrderItemRepository {
	int insert(OrderItem order);
	int insertItems(List<OrderItem> list);
	int update(OrderItem order);
	int delete(int id);
	List<OrderItem> findItemsByOrderId(int orderId);
}
