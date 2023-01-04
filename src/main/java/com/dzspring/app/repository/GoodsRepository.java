package com.dzspring.app.repository;

import com.dzspring.app.entity.Goods;

public interface GoodsRepository {
	int insert(Goods goods);
	int update(Goods goods);
	int delete(int id);
}
