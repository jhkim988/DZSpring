package com.dzspring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/form")
public class FormController {

	@RequestMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@RequestMapping("/findIdForm")
	public String findId() {
		return "findIdForm";
	}
	
	@RequestMapping("/findPwdForm")
	public String findPwd() {
		return "findPwdForm";
	}
	
	@RequestMapping("/registerForm")
	public String registerForm() {
		return "registerForm";
	}
}
