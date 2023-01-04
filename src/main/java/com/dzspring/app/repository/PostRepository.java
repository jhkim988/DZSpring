package com.dzspring.app.repository;

import java.util.List;

import com.dzspring.app.entity.Goods;

public interface PostRepository {
	List<Goods> findByCategoryLimit10(String category);
}

