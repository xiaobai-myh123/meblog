package com.myh.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myh.mapper.BlogMapper;
import com.myh.mapper.LeaveCommentsMapper;
import com.myh.pojo.Comment;
import com.myh.pojo.LeaveComments;
import com.myh.service.LeaveCommentsService;

/*
**2020年6月27日--下午4:28:37
**@version:
**莫耀华:
**@Description:
*/
@Service
public class LeaveCommentsServiceImpl implements LeaveCommentsService{
	@Autowired
	private LeaveCommentsMapper leaveCommentsMapper;
	
	//插入留言
	@Override
	public int insertLeaveCommen(LeaveComments leaveComments) {
		Date date = new Date();
		leaveComments.setCreateTime(date);
		return leaveCommentsMapper.insertLeaveCommen(leaveComments);
	}
	
	//获取留言
	@Override
	public List<LeaveComments> getListLeaveComments() {
		List<LeaveComments> pList = leaveCommentsMapper.getListLeaveComments();//一级 评论
		List<LeaveComments> listNot = leaveCommentsMapper.getListLeaveCommentsNot();//  二级评论
		
		for (int i = 0; i < listNot.size(); i++) {
			Long pid = listNot.get(i).getParentConmmentId();
			//注入二级评论
			List<LeaveComments> geteaveCommentsByPid = geteaveCommentsByPid(pid);
			for (int j = 0; j < pList.size(); j++) {
				if (pList.get(j).getId() == pid) { 
					pList.get(j).setReplyComments(geteaveCommentsByPid);//注入子集
				}else {
					for (int k = 0; k < pList.get(j).getReplyComments().size(); k++) {
						// 追加子集评论 父级不是第一级的
						if (pList.get(j).getReplyComments().get(k).getId() == pid) {
							pList.get(j).getReplyComments().addAll(geteaveCommentsByPid);
						}
					}
				}
				for (int k = 0; k < pList.get(j).getReplyComments().size(); k++) {
					long id = pList.get(j).getReplyComments().get(k).getParentConmmentId();
					LeaveComments leaveComments = leaveCommentsMapper.geteaveCommentsById(id);// 注入二级评论的父评论
					pList.get(j).getReplyComments().get(k).setParentConmment(leaveComments);
				}
				
				
			}
		}
		
		
		
		return leaveCommentsMapper.getListLeaveComments();
	}

	@Override
	public List<LeaveComments> getListLeaveCommentsNot() {
		return leaveCommentsMapper.getListLeaveCommentsNot();
	}
	//根据父评论查找评论
	@Override
	public List<LeaveComments> geteaveCommentsByPid(Long pid) {
		return leaveCommentsMapper.geteaveCommentsByPid(pid);
	}
	
	//根据父评论id返回父评论
	@Override
	public LeaveComments geteaveCommentsById(Long pid) {
		return leaveCommentsMapper.geteaveCommentsById(pid);
	}

}
