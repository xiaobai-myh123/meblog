package com.myh.mapper;
/*
**2020年6月27日--上午11:31:17
**@version:
**莫耀华:
**@Description: 网页留言
*/


import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.myh.pojo.LeaveComments;


@Mapper
//@CacheNamespace
public interface LeaveCommentsMapper {
	
	@Insert("INSERT into t_leave_comments(admin_comment,avatar,content,create_time,name,email) values(#{adminComment},#{avatar},#{content},#{createTime},#{name},#{email})")
	public int insertLeaveCommen(LeaveComments leaveComments);
	
	@Select("select * from t_leave_comments")
	public List<LeaveComments> getListLeaveComments();
}
