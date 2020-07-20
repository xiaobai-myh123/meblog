package com.myh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.utils.RedisUtils;

/*
**2020年6月10日--下午6:17:30
**@version:
**莫耀华:
**@Description:
*/
@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisUtils redisUtils;
	@Test
	void redisGetTest() {
		redisUtils.set("a", "a");
		System.out.println(redisUtils.get("a"));
	}
	

}
