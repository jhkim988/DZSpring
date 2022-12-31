package com.dzspring.app.controller;

import java.nio.charset.Charset;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;

import lombok.Data;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Data
	private static class ResponseMessage {
		private StatusEnum status;
		private String message;
		private Object object;
	}
	
	private static enum StatusEnum {
		OK(200, "OK")
		, BAD_REQUEST(400, "BAD_REQUEST")
		, NOT_FOUND(404, "NOT_FOUND")
		, INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");
		
		int status;
		String code;
		
		StatusEnum(int status, String code) {
			this.status = status;
			this.code = code;
		}
	}
	
	@Autowired
	private MemberService memberService;

	@RequestMapping(value="/login")
	public ResponseEntity<ResponseMessage> login(@RequestBody Member loginInfo, final Model model, HttpServletRequest request) {
		Optional<Member> member = memberService.login(loginInfo);
		member.ifPresent(m -> request.getSession().setAttribute("member", m));
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ResponseMessage message = new ResponseMessage();
		message.setStatus(StatusEnum.OK);
		message.setMessage("로그인 성공!");
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@RequestMapping("/logout")
	public ResponseEntity<ResponseMessage> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("member");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		ResponseMessage message = new ResponseMessage();
		message.setStatus(StatusEnum.OK);
		message.setMessage("로그아웃 성공!");
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public Member view(HttpServletRequest request) {
		return (Member) request.getSession().getAttribute("member");
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String register(@RequestBody Member registerInfo) {
//		memberService.register(registerInfo);
		return "";
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public String update(@RequestBody Member updateInfo) {
		return "";
	}

	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public String delete(@RequestBody String id) {
		return "";
	}

	@RequestMapping(value = "/hasMember/{type}/{value}")
	public String hasMember(@PathVariable("type") String type, @PathVariable("value") String value) {
		return "";
	}

	@RequestMapping(value = "/findId/{method}/{value}")
	public String findId(@PathVariable("method") String method, @PathVariable("value") String value) {
		return "";
	}

	@RequestMapping(value = "/initPwd/{id}")
	public String initPwd(@PathVariable("id") String id) {
		return "";
	}
}
