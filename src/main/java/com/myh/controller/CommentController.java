package com.myh.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.pojo.Comment;
import com.myh.pojo.User;
import com.myh.service.impl.CommentServiceImpl;

/*
**2020年6月26日--下午9:48:14
**@version:
**莫耀华:
**@Description:
*/
@Controller
@Transactional
public class CommentController {
	
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 显示评论列表
	 * @param blogId
	 * @param model
	 * @return
	 */
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model){
	 	//博客的评论
      	List<Comment> comments = commentServiceImpl.getCommentById(blogId);
        model.addAttribute("comments",comments);
		return "blog::commentList";
	}
	/**
	 * 提交评论
	 * @param comment
	 * @return
	 */
	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session){
		User user = (User) session.getAttribute("user");
//		//管理员发 
		if(user != null){
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
			comment.setName(user.getNickname());
			comment.setEmail(user.getEmail());
		//普通用户
		} else { 
			comment.setAvatar("/images/xiaolian.jpg");
			comment.setAdminComment(false);
		}
//		commentServiceImpl.saveComment(comment);


//		System.out.println("comment"+comment);
//		System.out.println("comment"+comment.getParentConmment());
//		System.out.println("Blog.id"+comment.getBlog().getId());
//		System.out.println("user="+user);
		Long blogId = comment.getBlog().getId();
		int num = commentServiceImpl.insertComment(comment);
		if(num<=0) {
			logger.info("error", "留言失败"+comment.getBlog().getId()+" "+comment+new Date());
		}
		return "redirect:/comments/" +blogId;
	}
	
	
}
