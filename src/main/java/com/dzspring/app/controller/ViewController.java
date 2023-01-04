package com.dzspring.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

	@RequestMapping("/member")
	public String viewMember() {
		return "/view/member";
	}
}
