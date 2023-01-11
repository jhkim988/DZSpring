package com.dzspring.app.repository;

import java.util.Map;

import com.dzspring.app.entity.GoodsImage;

public interface GoodsImageRepository {
	int insert(GoodsImage goodsImage);
	int updateGoodsId(Map<String, Object> map);
}
