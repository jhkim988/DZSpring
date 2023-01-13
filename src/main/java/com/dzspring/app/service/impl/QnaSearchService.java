package com.dzspring.app.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dzspring.app.entity.Qna;
import com.dzspring.app.repository.QnaRepository;
import com.dzspring.app.service.Command;

@Service
public class QnaSearchService {

	private QnaRepository qnaRepository;
	private Map<String, Method> commandMap;
	
	@Autowired
	public QnaSearchService(QnaRepository qnaRepository) {
		this.qnaRepository = qnaRepository;
		this.commandMap = new ConcurrentHashMap<>();
		Arrays.asList(getClass().getDeclaredMethods()).forEach(method -> {
			Command command = method.getAnnotation(Command.class);
			if (command == null) return;
			commandMap.put(command.value(), method);
		});
	}
	
	@Command("all")
	public List<Qna> searchAll(Map<String, Object> map) {
		return qnaRepository.searchAll(map);
	}
	
	@Command("id")
	public List<Qna> searchById(Map<String, Object> map) {
		return qnaRepository.searchById(map);
	}
	
	@Command("goodsId")
	public List<Qna> searchByGoodsId(Map<String, Object> map) {
		return qnaRepository.searchByGoodsId(map);
	}
	
	@Command("type")
	public List<Qna> searchByType(Map<String, Object> map) {
		return qnaRepository.searchByType(map);
	}
	
	@Command("title")
	public List<Qna> searchByTitle(Map<String, Object> map) {
		return qnaRepository.searchByTitle(map);
	}
	
	@Command("memberId")
	public List<Qna> searchByMemberId(Map<String, Object> map) {
		return qnaRepository.searchByMemberId(map);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qna> invoke(Map<String, Object> map) {
		String searchType = (String) map.get("searchType");
		if (!commandMap.containsKey(searchType)) throw new UnsupportedOperationException();
		try {
			return (List<Qna>) commandMap.get(searchType).invoke(this, map);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
}
