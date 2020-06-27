package com.myh.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.myh.pojo.Tag;
import com.myh.pojo.Type;
import com.myh.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
**2020年6月13日--下午8:01:57
**@version:
**莫耀华:
**@Description: 前台展示的博客 bean
*/
@Data
@AllArgsConstructor	
@NoArgsConstructor
public class VoBlog implements Serializable{
	private Long id;
	private String title;
	private String description;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;
	private Long views;
	private String name;
	private String firstPicture;
	private User user;
	private Type type;
	private String tagIds;
	private List<Tag> tags;
}
