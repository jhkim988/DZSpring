package com.dzspring.app.entity;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goods {
	private int id;
	private String category;
	private String title;
	private String author;
	private String publisher;
	private long price;
	private Date publishedAt;
	private long totalPage;
	private String statusCode;
	private String intro;
	private String authorIntro;
	private String publisherIntro;
	private String recommendation;
	private Date createdAt;
	private String img;
	
	public static Goods mapToGoods(Map<String, Object> map) {
		Goods goods = Goods.builder()
				.category((String) map.get("category"))
				.title((String) map.get("title"))
				.author((String) map.get("author"))
				.publisher((String) map.get("publisher"))
				.price(Long.parseLong((String) map.get("price")))
				.publishedAt(Date.valueOf((String) map.get("publishedAt")))
				.totalPage(Long.parseLong((String) map.get("totalPage")))
				.statusCode((String) map.get("statusCode"))
				.intro((String) map.get("intro"))
				.authorIntro((String) map.get("authorIntro"))
				.publisherIntro((String) map.get("publisherIntro"))
				.recommendation((String) map.get("recommendation"))
				.build();
		if (map.containsKey("img")) {
			@SuppressWarnings("unchecked")
			List<String> imgs = (List<String>) map.get("img");
			if (imgs.size() > 0) goods.setImg(imgs.get(0));
		}
		return goods;
	}
}
