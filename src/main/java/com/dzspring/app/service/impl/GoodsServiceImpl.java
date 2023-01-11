package com.dzspring.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzspring.app.entity.Goods;
import com.dzspring.app.repository.GoodsImageRepository;
import com.dzspring.app.repository.GoodsRepository;
import com.dzspring.app.service.GoodsService;

@Service("goodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

	private final GoodsRepository goodsRepository;
	private final GoodsImageRepository goodsImageRepository;
	
	@Autowired
	public GoodsServiceImpl(GoodsRepository goodsRepository, GoodsImageRepository goodsImageRepository) {
		this.goodsRepository = goodsRepository;
		this.goodsImageRepository = goodsImageRepository;
	}
	
	@Override
	@Transactional
	public boolean insert(Map<String, Object> goods) {
		boolean result = 1 == goodsRepository.insert(Goods.mapToGoods(goods));
		
		@SuppressWarnings("unchecked")
		List<String> storedNames = (List<String>) goods.get("img");
		
		Map<String, Object> map = new HashMap<>();
		map.put("storedNames", storedNames);
		map.put("goodsId", goods.get("id"));
		goodsImageRepository.updateGoodsId(map);
		return result;
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
		return goodsRepository.toGoodsList(map);
	}
}
