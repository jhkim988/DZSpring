package com.dzspring.app.controller;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dzspring.app.entity.Member;
import com.dzspring.app.service.MemberService;

@Controller
@RequestMapping("/form")
public class FormController {

	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "/form/loginForm";
	}
	
	@RequestMapping("/findIdForm")
	public String findId() {
		return "/form/findIdForm";
	}
	
	@RequestMapping("/findPwdForm")
	public String findPwd() {
		return "/form/findPwdForm";
	}
	
	@RequestMapping("/registerForm")
	public String registerForm() {
		return "/form/registerForm";
	}
	
	@RequestMapping("/deleteMemberForm")
	public String deleteMember() {
		return "/form/deleteMemberForm";
	}
	
	@RequestMapping("/updateMemberForm")
	public String updateMember() {
		return "/form/updateMemberForm";
	}
	
	@RequestMapping("/adminUpdateMemberForm/{id}")
	public String adminUpdateMemberForm(@PathVariable String id, HttpServletRequest request) {
		Optional<Member> member = memberService.findOneById(id);
		member.ifPresent(mem -> request.setAttribute("manage_member", mem));
		return "/form/adminUpdateMemberForm";
	}
}
