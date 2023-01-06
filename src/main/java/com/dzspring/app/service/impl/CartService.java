package com.dzspring.app.service.impl;

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
	
	public List<Cart> list(Map<String, Object> map) {
		return cartRepository.list(map);
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
