package com.myh.service;

import com.myh.pojo.User;

/*
**2020年6月8日--上午10:45:59
**@version:
**莫耀华:
**@Description:
*/
public interface UserService {
	//用户的登录
	public User selectOne(String username, String password);
}
