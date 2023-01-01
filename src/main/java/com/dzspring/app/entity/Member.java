package com.dzspring.app.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
	private String id;
	@JsonIgnore
	private String pwd;
	private String name;
	private String email;
	private String phone;
	@JsonFormat(pattern="yyyy년 MM월 dd일")
	private Timestamp createdAt;
	@JsonFormat(pattern="yyyy년 MM월 dd일")
	private Timestamp updatedAt;
	private Integer authority;
}
