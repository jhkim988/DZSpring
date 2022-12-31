package com.dzspring.app.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping("/login")
	public String login(@RequestBody Member loginInfo, final Model model, HttpServletRequest request) {
		Optional<Member> member = memberService.login(loginInfo);
		member.ifPresent(m -> request.getSession().setAttribute("member", m));
		return "home";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("member");
		return "home";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String view(@RequestBody String id) {
		return "";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String register(@RequestBody Member registerInfo) {
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
