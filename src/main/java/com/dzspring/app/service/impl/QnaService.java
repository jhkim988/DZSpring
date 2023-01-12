package com.dzspring.app.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dzspring.app.entity.Member;
import com.dzspring.app.entity.Qna;
import com.dzspring.app.repository.QnaRepository;

@Service
public class QnaService {

	private final QnaRepository qnaRepository;
	
	@Autowired
	public QnaService(QnaRepository qnaRepository) {
		this.qnaRepository = qnaRepository;
	}
	
	public List<Qna> list(Map<String, Object> map) {
		int page = Integer.parseInt((String) map.get("page"));
		int offset = (page-1)*5;
		map.put("offset", offset);
		return qnaRepository.findByGoodsIdLimit10WithOffset(map);
	}
	
	@Transactional
	public boolean insert(Qna qna) {
		int insert = qnaRepository.insert(qna);
		qna.setParentId(qna.getId());
		int update = qnaRepository.update(qna);
		return insert == 1 && update == 1;
	}
	
	public boolean delete(Member member, int id) {
		Qna qna = qnaRepository.findOneById(id);
		if (qna == null || !qna.getMemberId().equals(member.getId())) return false;
		return 1 == qnaRepository.delete(id);
	}
	
	public int getCount(Map<String, Object> map) {
		return qnaRepository.getCount(map);
	}
}
