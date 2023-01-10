package com.dzspring.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Goods;
import com.dzspring.app.repository.GoodsRepository;
import com.dzspring.app.service.GoodsService;

@Service("goodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

	private final GoodsRepository goodsRepository;

	@Autowired
	public GoodsServiceImpl(GoodsRepository goodsRepository) {
		this.goodsRepository = goodsRepository;
	}
	
	@Override
	public boolean insert(Goods goods) {
		return 1 == goodsRepository.insert(goods);
	}

	@Override
	public boolean update(Goods goods) {
		return 1 == goodsRepository.update(goods);
	}

	@Override
	public boolean delete(int id) {
		return 1 == goodsRepository.delete(id);
	}
	
	@Override
	public Optional<Goods> findOneById(int id) {
		return Optional.ofNullable(goodsRepository.findOneById(id));
	}

	/**
	 * @param goodsId 의 목록
	 * @return Goods 객체의 목록
	 */
	@Override
	public List<Goods> toGoodsList(List<Integer> goodsIds) {
		Map<String, List<Integer>> map = new HashMap<>();
		map.put("list", goodsIds);
		return goodsRepository.cartToGoodsList(map);
	}
}
