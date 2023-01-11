package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Cart;

public interface CartRepository {
	int insert(Cart cart);
	int update(Cart cart);
	int delete(List<Integer> cartIds);
	List<Cart> listByMember(Map<String, Object> map);
	List<Cart> findByIdList(Map<String, List<Integer>> map);
}
