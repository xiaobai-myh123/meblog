package com.myh.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor	
@NoArgsConstructor
public class User implements Serializable{
	
	private Long id;
	private String nickname;//昵称
	private String username;//用户名
	private String password;//密码
	private String email;//邮箱
	private String avatar;//评论地址
	private Integer type;//类型
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;//
	
	private List<Blog> blogs=new ArrayList<Blog>();
}
