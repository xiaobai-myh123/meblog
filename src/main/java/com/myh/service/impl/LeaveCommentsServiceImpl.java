package com.myh.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myh.mapper.BlogMapper;
import com.myh.mapper.LeaveCommentsMapper;
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
	
	@Override
	public int insertLeaveCommen(LeaveComments leaveComments) {
		Date date = new Date();
		leaveComments.setCreateTime(date);
		return leaveCommentsMapper.insertLeaveCommen(leaveComments);
	}

	@Override
	public List<LeaveComments> getListLeaveComments() {
		return leaveCommentsMapper.getListLeaveComments();
	}

}
