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
	private String receiverName;
	private String receiverPhone;
	private String address;
	private String payMethod;
	private long totalPrice;
	private Timestamp createdAt;
}
