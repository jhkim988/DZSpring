package com.dzspring.app.repository;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.dzspring.app.entity.GoodsImage;

@Repository
public interface GoodsImageMybatisRepository {
	int insert(GoodsImage goodsImage);
	int updateGoodsId(Map<String, Object> map);
	GoodsImage findOneByStoredName(String stored_name);
}
