package com.myh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.myh.service.BlogService;

/*
**2020年6月24日--下午4:36:23
**@version:
**莫耀华:
**@Description:
*/
@Controller
public class AboutShowController {
	@Autowired
	private BlogService blogServiceImpl;
	@GetMapping("/about")
	public String about(Model model) {
		//最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		return "about";
	}
}
