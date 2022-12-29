package com.dzspring.app.entity;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {
	private int id;
	private int parent;
	private String author;
	private String title;
	private String content;
	private Timestamp createdAt;
	private String category;
	private int viewcount;
	private int good;
	private int bad;
}
