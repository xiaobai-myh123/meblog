package com.myh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.service.impl.BlogServiceImpl;

/*
**2020年9月14日--下午9:22:28
**@version:
**莫耀华:
**@Description:
*/
@SpringBootTest
public class PageHelper {

	@Autowired
	private BlogServiceImpl blogService;
	@Test
	public void test1() {
		blogService.getListEsBlog();
	}
	
	
}
