package com.dzspring.app.service;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Goods;

@Service("goodsService")
public interface GoodsService {
	boolean insert(Goods goods);
	boolean update(Goods goods);
	boolean delete(int id);
}
