package com.dzspring.app.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Goods;

@Service("goodsService")
public interface GoodsService {
	boolean insert(Goods goods);
	boolean update(Goods goods);
	boolean delete(int id);
	List<Goods> search(Map<String, Object> searchInfo);
}
