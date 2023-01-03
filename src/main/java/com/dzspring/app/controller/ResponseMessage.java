package com.dzspring.app.controller;

import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
	private String message;
	private String url;
	private Object data;
	
	public ResponseMessage(String message) {
		this.message = message;
	}
	
	public static HttpHeaders getJSONHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return headers;
	}
	
	public static String getContexPath() {
		return "/DZSpring/";
	}
}
