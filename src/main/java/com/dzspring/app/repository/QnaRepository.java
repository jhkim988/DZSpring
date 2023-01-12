package com.dzspring.app.repository;

import java.util.List;
import java.util.Map;

import com.dzspring.app.entity.Qna;

public interface QnaRepository {
	List<Qna> findByGoodsIdLimit10WithOffset(Map<String, Object> map);
	Qna findOneById(int id);
	int insert(Qna qna);
	int update(Qna qna);
	int delete(int id);
	int getCount(Map<String, Object> map);
}
