package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Order;

public interface OrderRepository {
	List<Order> list(Map<String, Object> map);
	int insert(Order order);
	int update(Order order);
	int delete(int id);
	Order findOneById(int id);
}
