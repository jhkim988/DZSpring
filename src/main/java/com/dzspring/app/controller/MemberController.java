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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping("/member")
public class MemberController {
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	private static class ResponseMessage {
		private String message;
		private String url;
		private Object data;
		
		public ResponseMessage(String message) {
			this.message = message;
		}
	}
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="/login")
	public ResponseEntity<ResponseMessage> login(@RequestBody Member loginInfo, final Model model, HttpServletRequest request) {
		Optional<Member> member = memberService.login(loginInfo);
		member.ifPresent(m -> request.getSession().setAttribute("member", m));	
		ResponseMessage message = new ResponseMessage("로그인 성공!");
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping("/logout")
	public ResponseEntity<ResponseMessage> logout(HttpServletRequest request) {
		request.getSession().removeAttribute("member");
		ResponseMessage message = new ResponseMessage("로그아웃 성공!");
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<ResponseMessage> view(HttpServletRequest request) {
		Member member = (Member) request.getSession().getAttribute("member");
		ResponseMessage message = new ResponseMessage("회원 상세보기");
		message.setData(member);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<ResponseMessage> register(@RequestBody Member registerInfo, HttpServletRequest request) {
		boolean result = memberService.register(registerInfo);

		ResponseMessage message = new ResponseMessage(result ? "회원 가입 성공!" : "일시적 오류");
		message.setData(result);
		if (result)	message.setUrl(request.getContextPath());
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> update(@RequestBody Member updateInfo, HttpServletRequest request) {
		boolean result = memberService.update(updateInfo);

		ResponseMessage message = new ResponseMessage(result ? "회원 정보 업데이트!" : "일시적 오류");
		message.setData(result);
		if (result)	message.setUrl(request.getContextPath());
		
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	// ISSUE: Delete Method -> Not allowd body
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> delete(@RequestBody Member deleteInfo, HttpServletRequest request) {
		boolean result = memberService.delete(deleteInfo);
		
		ResponseMessage message = new ResponseMessage(result ? "회원 탈퇴" : "일시적 오류");
		message.setData(result);
		if (result) message.setUrl(request.getContextPath());

		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/hasMember/{type}/{value}")
	public ResponseEntity<ResponseMessage> hasMember(@PathVariable("type") String type, @PathVariable("value") String value) {
		boolean result = memberService.hasMember(type, value);
		ResponseMessage message = new ResponseMessage(result ? "이미 사용 중입니다." : "");
		message.setData(result);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/findId/{method}/{value}")
	public ResponseEntity<ResponseMessage> findId(@PathVariable("method") String method, @PathVariable("value") String value) {
		Optional<Member> result = memberService.findId(method, value);
		ResponseMessage message = new ResponseMessage();
		message.setData(result.isPresent());
		result.ifPresentOrElse(
				member -> message.setMessage(member.getId())
				, () -> message.setMessage("일치하는 정보가 없습니다."));
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/initPwd/{id}")
	public ResponseEntity<ResponseMessage> initPwd(@PathVariable("id") String id, HttpServletRequest request) {
		boolean result = memberService.initPwd(id);
		ResponseMessage message = new ResponseMessage();
		message.setData(result);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	private HttpHeaders getJSONHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return headers;
	}
}