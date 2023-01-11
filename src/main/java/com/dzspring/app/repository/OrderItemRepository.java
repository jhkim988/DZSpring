package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.OrderItem;

public interface OrderItemRepository {
	int insert(OrderItem order);
	int insertItems(List<OrderItem> list);
	int update(OrderItem order);
	int delete(int id);
	List<OrderItem> findItemsByOrderId(int orderId);
	List<Map<String, Object>> getGoodsByOrderId(int id);
}
