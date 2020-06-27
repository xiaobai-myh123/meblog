package com.myh.mapper;
/*
**2020年6月26日--下午9:33:03
**@version:
**莫耀华:
**@Description: 评论模块的管理
*/

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.myh.pojo.Comment;

@Mapper
public interface CommentMapper{
	
	//插入一个评论
	public int insertComment(Comment comment);
	
	//通过博客id查找评论
	@Select("SELECT * from t_comment where blog_id=#{id} and parent_conmment_id=-1")
	public List<Comment> getCommentById(@Param("id") Long blogId);
	
	//通过博客id查找评论 -1代表二级评论
	@Select("SELECT * from t_comment where blog_id=#{id} and parent_conmment_id!=-1")
	public List<Comment> getCommentByIdNot(@Param("id") Long blogId);
	
	//通过博客id和父id查找评论
	@Select("SELECT * from t_comment where blog_id=#{id} and parent_conmment_id=#{pid}")
	public List<Comment> getCommentByIdAndByPId(@Param("id") Long blogId,@Param("pid") Long pid);
}  
