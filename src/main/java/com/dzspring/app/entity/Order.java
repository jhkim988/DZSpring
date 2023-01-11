package com.dzspring.app.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private String status;
	private long totalPrice;
	@JsonFormat(pattern = "yyyy년 MM 월 dd일")
	private Timestamp createdAt;
}
