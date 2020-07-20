package com.myh.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.myh.pojo.User;

@Mapper
//@CacheNamespace
public interface UserMapper {
	
	//用户的登录
	public User selectOne(@Param("username") String username,@Param("password") String password);
	
	//拿到用户的名字和照片
	@Select("select nickname,avatar from t_user")
	public User seleteNickNameAndAvatar();
}
