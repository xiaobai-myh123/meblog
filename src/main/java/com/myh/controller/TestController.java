
package com.myh.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/*
**2020年6月30日--上午11:40:34
**@version:
**莫耀华:
**@Description: 测试Controller异常
*/
@RestController
public class TestController {
	
	@GetMapping("/hello")
	public String hellp(HttpServletRequest request) {
	
		return request.getServletContext().getContextPath();
	}
	
}
