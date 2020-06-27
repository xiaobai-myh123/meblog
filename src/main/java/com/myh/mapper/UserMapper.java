package com.myh.mapper;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myh.pojo.User;

@Mapper
//@CacheNamespace
public interface UserMapper {
	
	//用户的登录
	public User selectOne(@Param("username") String username,@Param("password") String password);
	
}
