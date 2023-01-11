package com.dzspring.app.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Cart;
import com.dzspring.app.entity.Goods;
import com.dzspring.app.repository.CartRepository;
import com.dzspring.app.service.GoodsService;

@Service
public class CartService {

	private final CartRepository cartRepository;
	private final GoodsService goodsService;
	
	@Autowired
	public CartService(CartRepository cartRepository, GoodsService goodsService) {
		this.cartRepository = cartRepository;
		this.goodsService = goodsService;
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
		return 1 == delete(Arrays.asList(id));
	}
	
	public int delete(List<Integer> cartIds) {
		return cartRepository.delete(cartIds);
	}
	
	public long getTotalPrice(List<Integer> cartIds) {
		Map<String, List<Integer>> map = new HashMap<>();
		map.put("list", cartIds);
		List<Cart> carts = cartRepository.findByIdList(map);
		List<Goods> goods = goodsService.toGoodsList(carts.stream().map(Cart::getGoodsId).collect(Collectors.toList()));
		return IntStream.range(0, carts.size()).mapToObj(Integer::new)
					.reduce(0L, (total, idx) -> total + carts.get(idx).getQuantity()*goods.get(idx).getPrice(), (x, y) -> x+y);
	}
}
