package com.dzspring.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.repository.CartRepository;

@Service
public class CartService {

	private final CartRepository cartRepository;
	
	@Autowired
	public CartService(CartRepository cartRepository) {
		this.cartRepository = cartRepository;
	}
	
	/**
	 * @param map 에 memberId key 가 있어야 한다.
	 * @return 해당 member의 cart 목록을 반환한다.
	 */
	public List<Cart> listByMember(Map<String, Object> map) {
		return cartRepository.listByMember(map);
	}
	
	public List<Cart> findByIdList(List<Integer> ids) {
		Map<String, List<Integer>> map = new HashMap<>();
		map.put("list", ids);
		return cartRepository.findByIdList(map);
	}
	
	public boolean insert(Cart cart) {
		return 1 == cartRepository.insert(cart);
	}
	
	public boolean update(Cart cart) {
		return 1 == cartRepository.update(cart);
	}
	
	public boolean delete(int id) {
		return 1 == cartRepository.delete(id);
	}
}
