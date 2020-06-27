package com.myh;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.pojo.Blog;
import com.myh.pojo.Comment;
import com.myh.service.impl.CommentServiceImpl;

/*
**2020年6月26日--下午9:43:47
**@version:
**莫耀华:
**@Description:
*/
@SpringBootTest
public class CommetTest {

	@Autowired
	private CommentServiceImpl commentServiceImpl;
	@Test
	void contextLoads() {
//		Comment comment = new Comment();
//		comment.setAvatar("/images/xiaolian");
//		comment.setContent("我是测试");
//		comment.setCreateTime(new Date());
//		comment.setEmail("123@qq.com");
//		Blog blog = new Blog();
//		blog.setId(1275670130210111488L);
//		Comment parentConmment = new Comment();
//		parentConmment.setId(null);
//		comment.setParentConmment(parentConmment);
//		comment.setAdminComment(false);
//		System.out.println(commentServiceImpl.insertComment(comment));
		List<Comment> list = commentServiceImpl.getCommentById(1275670130210111488L);
		list.forEach(System.out::println);
	}
}
//Preparing: INSERT into t_comment (avatar,content,create_time,email,name,blog_id,parent_conmment_id,admin_comment) VALUES (?,?,?,?,?,?,?,?) 
//Parameters: /images/xiaolian.jpg(String), 1111(String), 2020-06-26 22:56:30.957(Timestamp), 123@qq.com(String), 游客(String), 1275670130210111488(Long), -1(Long), false(Boolean)
