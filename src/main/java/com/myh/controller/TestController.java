
package com.myh.controller;

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
	public String hellp() {
		int i=9/0;
		System.out.println(i);
		return "hellp";
	}
	
}
