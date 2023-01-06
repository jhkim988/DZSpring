package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Cart;

public interface CartRepository {
	List<Cart> list(Map<String, Object> map);
	int insert(Cart cart);
	int update(Cart cart);
	int delete(int id);
}
