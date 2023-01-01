package com.dzspring.app.controller;

import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;
import static com.dzspring.app.controller.ResponseMessage.*;

@RestController
@RequestMapping("/member")
public class MemberController {
	
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
		Optional<Member> member = memberService.update(updateInfo);
		
		ResponseMessage message = new ResponseMessage();
		message.setData(member.isPresent());
		member.ifPresentOrElse(mem -> {
				HttpSession session = request.getSession();
				session.setAttribute("member", mem);
				message.setUrl(request.getContextPath());
				message.setMessage("회원 정보 업데이트!");
			}
			, () -> message.setMessage("일시적 오류"));
		
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value="/validate")
	public ResponseEntity<ResponseMessage> validate(@RequestBody String inputPwd, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		boolean result = memberService.validate(member.getId(), inputPwd);
		ResponseMessage message = new ResponseMessage();
		if (result) session.setAttribute("validate", true);
		message.setData(result);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> delete(@PathVariable String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object isValidate = session.getAttribute("validate");
		if (isValidate != null && isValidate.getClass() == boolean.class && (boolean) isValidate) {
			boolean result = false;
			ResponseMessage message = new ResponseMessage("회원 탈퇴");
			try {
				result = memberService.delete(id);
			} catch (SQLException e) {
				message.setMessage("일시적 오류");
			}
			message.setData(result);
			if (result) message.setUrl(request.getContextPath());
			session.removeAttribute("validate");
			return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
		}
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
}