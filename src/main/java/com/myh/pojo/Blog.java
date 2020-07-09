package com.myh.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("博客实体类")
public class Blog implements Serializable{

	// 主键
	@ApiModelProperty(value = "博客的id",example = "0")
	private Long id;
	@ApiModelProperty(value = "博客的标题")
	private String title;
	@ApiModelProperty(value = "博客markdown内容")
	private String content; // 博客内容
	@ApiModelProperty(value = "博客的首图")
	private String firstPicture;// 首图
	@ApiModelProperty(value = "标记 是否为原创转载或翻译")
	private String flag;// 标记
	@ApiModelProperty(value = "博客前台内容显示")
	private String description;// 描述
	@ApiModelProperty(value = "博客浏览次数",example = "0")
	private Integer views=0;// 浏览次数
	@ApiModelProperty(value = "博客赞赏是否开启")
	private boolean appreciation;// 赞赏是否开启
	@ApiModelProperty(value = "博客转载声明是否开启")
	private boolean sharStatement;// 转载声明是否开启
	@ApiModelProperty(value = "博客评论是否开启")
	private boolean commentabled;// 评论是否开启
	@ApiModelProperty(value = "博客是否发布")
	private boolean publicshed;// 是否发布
	@ApiModelProperty(value = "博客是否推荐")
	private boolean recommend;// 是否推荐
	@ApiModelProperty(value = "博客创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//
	@ApiModelProperty(value = "博客更新时间")
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
