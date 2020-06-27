package com.myh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.service.impl.UserServiceImpl;
import com.myh.utils.MD5Utils;

@SpringBootTest
class UserTest {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Test
	void contextLoads() {
		System.out.println(userServiceImpl.selectOne("1",MD5Utils.code("1")));
	}

}
