package com.dzspring.app.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Qna {
	private int id;
	private int goodsId;
	private int parentId;
	private String type;
	private String title;
	private String content;
	private String memberId;
	@JsonFormat(pattern = "yyyy년 MM월 dd일")
	private Timestamp createdAt;
}
