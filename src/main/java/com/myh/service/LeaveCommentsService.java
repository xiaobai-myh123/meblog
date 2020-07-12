package com.myh.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.myh.pojo.LeaveComments;

/*
**2020年6月27日--下午4:28:01
**@version:
**莫耀华:
**@Description:
*/
public interface LeaveCommentsService {

	public int insertLeaveCommen(LeaveComments leaveComments);
	//一级评论
	public List<LeaveComments> getListLeaveComments();
	
	//二级评论
	public List<LeaveComments> getListLeaveCommentsNot();
	
	//根据父评论查找评论
	public List<LeaveComments> geteaveCommentsByPid(Long pid);
	
	//根据父评论id返回父评论
	public LeaveComments geteaveCommentsById(Long pid);
}
