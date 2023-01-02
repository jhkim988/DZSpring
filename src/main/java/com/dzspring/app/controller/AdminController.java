package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.service.MemberService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/deleteMembers")
	public ResponseEntity<ResponseMessage> deleteMembers(@RequestBody(required=true) HashMap<String, Object> queryInfo) {
		@SuppressWarnings("unchecked")
		List<String> ids = (List<String>) queryInfo.get("ids");
		ResponseMessage message = new ResponseMessage();
		try {
			message.setData(memberService.delete(ids));
			message.setMessage("삭제 성공");
		} catch (SQLException e) {
			e.printStackTrace();
			message.setMessage("일시적 오류");
		}
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping("/memberSearch")
	public ResponseEntity<ResponseMessage> search(@RequestBody HashMap<String, String> searchInfo) {
		ResponseMessage message = new ResponseMessage();
		message.setData(memberService.list(searchInfo));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/urlAuth", method=RequestMethod.GET)
	public ResponseEntity<ResponseMessage> urlAuthlist() {
		return null;
	}
	
	@RequestMapping(value="/urlAuth", method=RequestMethod.POST)
	public ResponseEntity<ResponseMessage> insertUrlAuth() {
		return null;
	}
	
	@RequestMapping(value="/urlAuth/{id}", method=RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> updateUrlAuth() {
		return null;
	}
	
	@RequestMapping(value="/urlAuth/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> updateUrlDelete() {
		return null;
	}
}
