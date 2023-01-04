package com.dzspring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {

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
}
