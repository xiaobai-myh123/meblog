package com.myh.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myh.mapper.CommentMapper;
import com.myh.pojo.Comment;
import com.myh.service.CommentService;

/*
**2020年6月26日--下午9:37:57
**@version:
**莫耀华:
**@Description:
*/
@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	private CommentMapper commentMapper;
	
	@Override
	public int insertComment(Comment comment) {
		// TODO Auto-generated method stub
		Date date = new Date();
		comment.setCreateTime(date);
		return commentMapper.insertComment(comment);
	}

	@Override
	public List<Comment> getCommentById(Long blogId) {
		List<Comment> list = commentMapper.getCommentById(blogId);
		List<Comment> listNot = commentMapper.getCommentByIdNot(blogId);
		for (int i = 0; i < listNot.size(); i++) {
			Long pid = listNot.get(i).getParentConmmentId();
			List<Comment> commentChild = getCommentByIdAndByPId(blogId, pid);
			for (int j = 0; j < list.size(); j++) {
				if(list.get(j).getId()==pid) {
					list.get(j).setReplyComments(commentChild);
				}
			}
			
		}
//		list.forEach(System.out::println);
		return list;
	}

	@Override
	public List<Comment> getCommentByIdAndByPId(Long blogId, Long pid) {
		return commentMapper.getCommentByIdAndByPId(blogId, pid);
	}
//[Comment(id=6, name=游客1, email=123@qq.com, content=我是测试, avatar=/images/xiaolian.jpg, adminComment=false, createTime=Fri Jun 26 23:11:58 CST 2020, blog=null, 

	@Override
	public List<Comment> getCommentByIdNot(Long blogId) {
		// TODO Auto-generated method stub
		return commentMapper.getCommentByIdNot(blogId);
	}

}
