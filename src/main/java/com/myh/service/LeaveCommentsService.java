package com.myh.service;

import java.util.List;

import com.myh.pojo.LeaveComments;

/*
**2020年6月27日--下午4:28:01
**@version:
**莫耀华:
**@Description:
*/
public interface LeaveCommentsService {

	public int insertLeaveCommen(LeaveComments leaveComments);
	public List<LeaveComments> getListLeaveComments();
}
