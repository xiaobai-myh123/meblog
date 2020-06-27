package com.myh.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客实体类
 * 
 * @author 莫耀华
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable{

	// 主键
	private Long id;
	private String title;

	private String content; // 博客内容
	private String firstPicture;// 首图
	private String flag;// 标记
	private String description;// 描述
	private Integer views=0;// 浏览次数
	private boolean appreciation;// 赞赏是否开启
	private boolean sharStatement;// 转载声明是否开启
	private boolean commentabled;// 评论是否开启
	private boolean publicshed;// 是否发布
	private boolean recommend;// 是否推荐
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;//

	private String tagIds;

	public void init() {
		this.tagIds = tagsToIds(this.getTags());
	}

	// 1,2,3
	private String tagsToIds(List<Tag> tags) {
		if (!tags.isEmpty()) {
			StringBuffer ids = new StringBuffer();
			boolean flag = false;
			for (Tag tag : tags) {
				if (flag) {
					ids.append(",");
				} else {
					flag = true;
				}
				ids.append(tag.getId());
			}
			return ids.toString();
		} else {
			return tagIds;
		}
	}

	// 表关系
	private Type type; // 博客类型

	// 增加博客 时 也可以增加标签
	private List<Tag> tags = new ArrayList<Tag>();// 博客标签

	// 多对一的关系
	private User user;

	
	private List<Comment> comments = new ArrayList<Comment>();// 评论关系
}
