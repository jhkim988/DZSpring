package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Goods;

public interface GoodsRepository {
	int insert(Goods goods);
	int update(Goods goods);
	int delete(int id);
	Goods findOneById(int goods_id);
	List<Goods> findAllLimit10(Map<String, Object> map);
	List<Goods> findByCategoryLimit10(Map<String, Object> map);
	List<Goods> findByTitleLimit10(Map<String, Object> map);
	List<Goods> findByAuthorLimit10(Map<String, Object> map);
	List<Goods> findByPublisherLimit10(Map<String, Object> map);
	List<Goods> findByPriceLimit10(Map<String, Object> map);
	List<Goods> findByPublishedAtLimit10(Map<String, Object> map);
	List<Goods> findByPageLimit10(Map<String, Object> map);
	List<Goods> findByStatusCodeLimit10(Map<String, Object> map);
	List<Goods> findByCreatedAtLimit10(Map<String, Object> map);
}
