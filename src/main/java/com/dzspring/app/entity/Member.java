package com.dzspring.app.entity;

import java.sql.Timestamp;

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
	private String pwd;
	private String name;
	private String email;
	private String phone;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Integer authority;
}
