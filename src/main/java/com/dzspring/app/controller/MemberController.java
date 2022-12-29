package com.dzspring.app.controller;

import java.lang.reflect.Member;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	// TODO: JSON
	@RequestMapping("/login")
	public String login(@ModelAttribute("loginInfo") Member loginInfo, final Model model, HttpServletRequest request) {
		Optional<Member> member = memberService.login(loginInfo);
		member.ifPresent(m -> request.getSession().setAttribute("member", m));
		return "home";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().removeAttribute("member");
	}
}
