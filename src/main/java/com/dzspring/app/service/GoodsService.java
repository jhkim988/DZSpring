package com.dzspring.app.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Goods;

@Service("goodsService")
public interface GoodsService {
	boolean insert(Map<String, Object> goodsInfo);
	boolean update(Goods goods);
	boolean delete(int id);
	Optional<Goods> findOneById(int id);
	List<Goods> toGoodsList(List<Integer> list);
}
