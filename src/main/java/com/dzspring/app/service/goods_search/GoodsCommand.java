package com.dzspring.app.service.goods_search;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dzspring.app.entity.Goods;
import com.dzspring.app.repository.GoodsRepository;
import com.dzspring.app.service.Command;

@Component
public class GoodsCommand {
	
	@Autowired
	private GoodsRepository repository;
	
	@Command("all")
	public List<Goods> allSearch(Map<String, Object> map) {
		System.out.println("HERE:" + map);
		return repository.findAllLimit10(map);
	}

	@Command("category")
	public List<Goods> categorySearch(Map<String, Object> map) {
		return repository.findByCategoryLimit10(map);
	}
	
	@Command("title")
	public List<Goods> titleSearch(Map<String, Object> map) {
		return repository.findByTitleLimit10(map);
	}

	@Command("author")
	public List<Goods> authorSearch(Map<String, Object> map) {
		return repository.findByAuthorLimit10(map);
	}
	
	@Command("publisher")
	public List<Goods> publisherSearch(Map<String, Object> map) {
		return repository.findByPublisherLimit10(map);
	}
	
	@Command("price")
	public List<Goods> priceSearch(Map<String, Object> map) {
		return repository.findByPriceLimit10(map);
	}
	
	@Command("publishedAt")
	public List<Goods> publishedAtSearch(Map<String, Object> map) {
		System.out.println("HERE:" + map);
		return repository.findByPublishedAtLimit10(map);
	}
	
	@Command("page")
	public List<Goods> pageSearch(Map<String, Object> map) {
		return repository.findByPageLimit10(map);
	}
	
	@Command("statusCode")
	public List<Goods> statusCodeSearch(Map<String, Object> map) {
		return repository.findByStatusCodeLimit10(map);
	}
	
	@Command("createdAt")
	public List<Goods> createdAtSearch(Map<String, Object> map) {
		return repository.findByCreatedAtLimit10(map);
	}
	
	public boolean hasMethod(String method) {
		return GoodsCommandMap.MAP.getMap().containsKey(method);
	}
	
	@SuppressWarnings("unchecked")
	public List<Goods> invoke(String method, Map<String, Object> value) {
		List<Goods> goods = new ArrayList<>();
		try {
			goods = (List<Goods>) GoodsCommandMap.MAP.getMap().get(method).invoke(this, value);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return goods;
	}
}
