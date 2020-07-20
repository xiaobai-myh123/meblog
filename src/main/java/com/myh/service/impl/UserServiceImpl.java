package com.myh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myh.mapper.UserMapper;
import com.myh.pojo.User;
import com.myh.service.UserService;
import com.myh.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	//用户的登录
	public User selectOne(String username, String password) {
		return userMapper.selectOne(username,MD5Utils.code(password));
	}

	@Override
	public User seleteNickNameAndAvatar() {
		return userMapper.seleteNickNameAndAvatar();
	}
	
}
