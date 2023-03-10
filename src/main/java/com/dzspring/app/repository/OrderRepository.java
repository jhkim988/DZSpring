package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Order;

public interface OrderRepository {
	int insert(Order order);
	int update(Order order);
	int updateReceiverInfo(Order order);
	int delete(int id);
	List<Order> list(Map<String, Object> map);
	Order findOneById(int id);
	List<Order> findLimit10(Map<String, String> map);
	List<Order> findByMemberIdLimit10(Map<String, String> map);
}
