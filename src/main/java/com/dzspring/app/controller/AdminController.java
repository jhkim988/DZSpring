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

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;
import com.dzspring.app.service.impl.AdminMemberSearchService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	private final MemberService memberService;
	private final AdminMemberSearchService adminMemberSearchService;
	
	@Autowired
	public AdminController(MemberService memberService, AdminMemberSearchService adminMemberSearchService) {
		this.memberService = memberService;
		this.adminMemberSearchService = adminMemberSearchService;
	}
	
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
	public ResponseEntity<ResponseMessage> search(@RequestBody HashMap<String, Object> searchInfo) {
		ResponseMessage message = new ResponseMessage();
		message.setData(adminMemberSearchService.list(searchInfo));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping("/updateMember")
	public ResponseEntity<ResponseMessage> updateMember(@RequestBody Member member) {
		ResponseMessage message = new ResponseMessage();
		message.setData(memberService.update(member));
		message.setUrl(ResponseMessage.path("admin/member"));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}
