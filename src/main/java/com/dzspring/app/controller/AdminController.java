package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.service.MemberService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/deleteMembers")
	public ResponseEntity<ResponseMessage> deleteMembers(@RequestParam(value="id", required=true) List<String> ids) {
		ResponseMessage message = new ResponseMessage();
		try {
			message.setData(memberService.delete(ids));
			message.setMessage("삭제 성공");
		} catch (SQLException e) {
			message.setMessage("일시적 오류");
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}
