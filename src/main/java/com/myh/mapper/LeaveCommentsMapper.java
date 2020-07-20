package com.myh.mapper;
/*
**2020年6月27日--上午11:31:17
**@version:
**莫耀华:
**@Description: 网页留言
*/


import java.util.List;

import javax.annotation.security.PermitAll;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.myh.pojo.LeaveComments;


@Mapper
//@CacheNamespace
public interface LeaveCommentsMapper {
	
	@Insert("INSERT into t_leave_comments(admin_comment,avatar,content,create_time,name,email,parent_conmment_id) values(#{adminComment},#{avatar},#{content},#{createTime},#{name},#{email},#{parentConmmentId})")
	public int insertLeaveCommen(LeaveComments leaveComments);
	
	//一级评论
	@Select("select * from t_leave_comments WHERE parent_conmment_id=-1")
	public List<LeaveComments> getListLeaveComments();
	
	//二级评论
	@Select("select * from t_leave_comments WHERE parent_conmment_id!=-1")
	public List<LeaveComments> getListLeaveCommentsNot();
	
	//根据父评论查找评论
	@Select("select * from t_leave_comments WHERE parent_conmment_id=#{pid}")
	public List<LeaveComments> geteaveCommentsByPid(@Param("pid") Long pid);
	
	//根据父评论id返回父评论
	@Select("select * from t_leave_comments WHERE id=#{pid}")
	public LeaveComments geteaveCommentsById(@Param("pid") Long pid);
	
	
}
