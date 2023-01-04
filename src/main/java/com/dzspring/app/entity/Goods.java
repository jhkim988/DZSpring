package com.dzspring.app.entity;

import java.sql.Date;

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
	private String content;
	private String publisher;
	private long price;
	private Date publishedDate;
	private long totalPage;
	private String goodsStatus;
	private String intro;
	private String authorIntro;
	private String publisherComment;
	private String recommendation;
	private Date createdAt;
}
