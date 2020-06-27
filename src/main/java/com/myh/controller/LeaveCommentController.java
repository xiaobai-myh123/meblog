package com.myh.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myh.pojo.LeaveComments;
import com.myh.pojo.User;
import com.myh.service.BlogService;
import com.myh.service.impl.LeaveCommentsServiceImpl;

/*
**2020年6月27日--上午10:39:24
**@version:
**莫耀华:
**@Description:
*/
@Controller
@Transactional 
public class LeaveCommentController {
	
	@Autowired
	private BlogService blogServiceImpl;
	
	@Autowired
	private LeaveCommentsServiceImpl leaveCommentsServiceImpl;
	
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
	@GetMapping("/leaveMessage")
	public String goLeaveCommentPage(Model model) {
		//留言
		model.addAttribute("leaveComments", leaveCommentsServiceImpl.getListLeaveComments());
		//最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		System.out.println(leaveCommentsServiceImpl.getListLeaveComments());
		return "leaveComments";
	} 
	
	@GetMapping("/leaveMessageAjax")
	public String goLeaveCommentPageAjax(Model model) {
		//留言
		model.addAttribute("leaveComments", leaveCommentsServiceImpl.getListLeaveComments());
		//最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		return "leaveComments::leaveCommentsList";
	}
	//获取表单信息
	@PostMapping("/postGetLeaveComment")
	public String postGetLeaveComment(LeaveComments leaveComments,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user != null){
			leaveComments.setAvatar(user.getAvatar());
			leaveComments.setAdminComment(true);
			leaveComments.setName(user.getNickname());
			leaveComments.setEmail(user.getEmail());
		}else {
			leaveComments.setAvatar("/images/xiaolian.jpg");
			leaveComments.setAdminComment(false);
		}
		System.out.println(leaveComments);
		int count = leaveCommentsServiceImpl.insertLeaveCommen(leaveComments);
		if(count<=0) {
			Date date = new Date();
			logger.info("error", "留言失败"+leaveComments);
		}
		return "redirect:/leaveMessageAjax";
	}
	
}
