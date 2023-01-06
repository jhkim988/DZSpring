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
public class Order {
	private int id;
	private String memberId;
	private int goodsId;
	private int quantity;
	private String receiverName;
	private String receivetPhone;
	private String address;
	private String payMethod;
	private Timestamp createdAt;
}
