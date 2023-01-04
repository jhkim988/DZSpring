package com.dzspring.app.controller;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;
import static com.dzspring.app.controller.ResponseMessage.*;

@RestController
@RequestMapping("/test")
public class TestController {
	
	
	private final MemberService memberService;
	
	@Autowired
	public TestController(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@RequestMapping("/generateMember")
	public ResponseEntity<ResponseMessage> generateMember() {
		for (int i = 0; i < 100; i++) {
			memberService.register(Member.builder()
					.id("user"+i)
					.pwd("pwd")
					.name("자바좋아"+i)
					.sex("M")
					.email("java" + i + "@naver.com")
					.phone("0100000000"+i)
					.birth(Date.valueOf("1900-01-01")
)					.build());
		}
		return new ResponseEntity<>(null, getJSONHeader(), HttpStatus.OK);
	}
}
