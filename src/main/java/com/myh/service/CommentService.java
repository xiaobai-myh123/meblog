package com.myh.service;
/*
**2020年6月26日--下午9:36:40
**@version:
**莫耀华:
**@Description:
*/

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myh.pojo.Comment;

public interface CommentService {
	//插入一个评论
	public int insertComment(Comment comment);
	
	//查找所有评论
	public List<Comment> getCommentById(@Param("id") Long blogId);
	
	//通过博客id和父id查找评论
	public List<Comment> getCommentByIdAndByPId(@Param("id") Long blogId,@Param("pid") Long pid);
	//通过博客id查找评论 -1代表二级评论
	public List<Comment> getCommentByIdNot(@Param("id") Long blogId);
}
