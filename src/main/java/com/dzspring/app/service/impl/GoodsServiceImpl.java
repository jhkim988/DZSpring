package com.dzspring.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Goods;
import com.dzspring.app.repository.GoodsRepository;
import com.dzspring.app.service.GoodsService;
import com.dzspring.app.service.goods_search.GoodsCommand;

@Service("goodsServiceImpl")
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private GoodsCommand goodsCommand;
	
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
	public List<Goods> search(Map<String, Object> searchInfo) {
		String method = (String) searchInfo.get("method");
		@SuppressWarnings("unchecked")
		Map<String, Object> value = (Map<String, Object>) searchInfo.get("value");
		if (!goodsCommand.hasMethod(method)) throw new UnsupportedOperationException();
		return goodsCommand.invoke(method, value);
	}
}
