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
public class GoodsImage {
	private long goods_id;
	private String org_name;
	private String stored_name;
	private String content_type;
	private long content_length;
	private Timestamp createdAt;
}
