package com.myh.pojo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
**2020年6月27日--上午10:18:01
**@version:
**莫耀华:
**@Description: 留言模块
*/
@Data
@AllArgsConstructor	
@NoArgsConstructor
public class LeaveComments implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Boolean adminComment;//是否为管理员
	private String avatar;//默认头像
	private String content;//评论内容
	private Date createTime;
	private String name;
	private String  email;
}
