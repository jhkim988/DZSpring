package com.dzspring.app.controller;

import static com.dzspring.app.controller.ResponseMessage.getJSONHeader;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.EmailService;
import com.dzspring.app.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private EmailService emailService;
	
	@RequestMapping(value = "/login")
	public ResponseEntity<ResponseMessage> login(@RequestBody Member loginInfo, HttpServletRequest request) {
		Optional<Member> member = memberService.login(loginInfo);
		ResponseMessage message = new ResponseMessage("로그인 실패!");
		Optional<String> prevPageOpt = Arrays.asList(request.getCookies()).stream()
											.filter(x -> "prevPage".equals(x.getName()))
											.map(x -> x.getValue()).findAny();
		String movePage = prevPageOpt.orElse(ResponseMessage.getContexPath());
		member.ifPresent(m -> {
			request.getSession().setAttribute("member", m);
			message.setMessage("로그인 성공!");
			message.setUrl(movePage);
		});
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
		if (result)
			message.setUrl(request.getContextPath());
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.PUT)
	public ResponseEntity<ResponseMessage> update(@RequestBody Member updateInfo, HttpServletRequest request) {
		Optional<Member> member = memberService.update(updateInfo);

		ResponseMessage message = new ResponseMessage("일시적 오류!");
		message.setData(member.isPresent());
		member.ifPresent(mem -> {
			HttpSession session = request.getSession();
			session.setAttribute("member", mem);
			message.setUrl(request.getContextPath());
			message.setMessage("회원 정보 업데이트!");
		});
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/validate")
	public ResponseEntity<ResponseMessage> validate(@RequestBody HashMap<String, String> input,
			HttpServletRequest request) {
		String inputPwd = input.get("pwd");
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		ResponseMessage message = new ResponseMessage();
		if (member == null) {
			message.setData(Boolean.valueOf(false));
			return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.METHOD_NOT_ALLOWED);
		}
		boolean result = memberService.validate(member.getId(), inputPwd);
		if (result)
			session.setAttribute("validate", true);
		message.setData(result);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<ResponseMessage> delete(@PathVariable String id, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object isValidate = session.getAttribute("validate");
		if (isValidate != null && isValidate.getClass() == Boolean.class && (Boolean) isValidate) {
			boolean result = false;
			ResponseMessage message = new ResponseMessage("회원 탈퇴");
			try {
				result = memberService.delete(id);
			} catch (SQLException e) {
				e.printStackTrace();
				message.setMessage("일시적 오류");
			}
			message.setData(result);
			if (result)
				message.setUrl(request.getContextPath());
			session.removeAttribute("validate");
			return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value = "/hasMember")
	public ResponseEntity<ResponseMessage> hasMember(@RequestBody HashMap<String, String> queryInfo) {
		String type = queryInfo.get("type");
		String value = queryInfo.get("value");
		if (type == null || value == null) {
			return new ResponseEntity<>(null, getJSONHeader(), HttpStatus.BAD_REQUEST);
		}
		boolean result = memberService.hasMember(type, value);
		ResponseMessage message = new ResponseMessage(result ? "이미 사용 중입니다." : "사용 가능합니다.");
		message.setData(result);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}

	@RequestMapping(value = "/findId")
	public ResponseEntity<ResponseMessage> findId(@RequestBody HashMap<String, String> queryInfo) {
		String method = queryInfo.get("method");
		String name = queryInfo.get("name");
		String value = queryInfo.get("value");
		Optional<Member> result = memberService.findMemberBy(method, name, value);
		ResponseMessage message = new ResponseMessage();
		Map<String, Object> data = new HashMap<>();
		data.put("result", result.isPresent());
		result.ifPresent(member -> data.put("id", member.getId()));
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/tryInitPwd")
	public ResponseEntity<ResponseMessage> tryInitPwd(@RequestBody HashMap<String, String> queryInfo) {
		String type = queryInfo.get("type");
		String name = queryInfo.get("name");
		String value = queryInfo.get("value");
		ResponseMessage message = new ResponseMessage("일치하는 정보가 없습니다.");
		message.setData(false);
		Optional<Member> member = memberService.findMemberBy(type, name, value);
		member.ifPresent(mem -> {
			String auth = memberService.generateInitPwd(mem);
			emailService.sendInitPwd(mem.getEmail(), auth);
			message.setMessage("등록된 이메일 주소로 이메일을 발송하였습니다.");
			message.setData(true);
		});
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/initPwdAuth")
	public ResponseEntity<ResponseMessage> initPwdAuth(@RequestBody HashMap<String, String> map) {
		String id = map.get("id");
		String auth = map.get("auth");
		Optional<String> initPwd = memberService.initPwdAuth(id, auth);
		Map<String, Object> data = new HashMap<>();
		initPwd.ifPresent(pwd -> {
			data.put("result", true);
			data.put("initPwd", pwd);
			memberService.deleteTmpPwd(id);
		});
		ResponseMessage message = new ResponseMessage();
		message.setData(data);
		return new ResponseEntity<>(message, getJSONHeader(), HttpStatus.OK);
	}
}