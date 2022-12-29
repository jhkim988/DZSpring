package com.dzspring.app.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dzspring.app.service.PostService;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final List<String> categories = Arrays.asList("notices", "qnas", "generals");

	@Autowired
	private PostService postService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		categories
			.forEach(category -> model.addAttribute(category, postService.getHeadlineWithCategory(category)));
		return "home";
	}
}
