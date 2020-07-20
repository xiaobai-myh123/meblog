package com.myh.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
**2020年7月13日--下午10:28:30
**@version:
**莫耀华:
**@Description:
*/
/**
 * es搜索博客的实体对象
 * @author 莫耀华
 *
 */
@Data
@AllArgsConstructor	
@NoArgsConstructor
@ApiModel("es搜索博客")
public class ESBlog {
	@ApiModelProperty(value = "博客的id",example = "0")
	private Long id;
	@ApiModelProperty(value = "博客的标题")
	private String title;
	@ApiModelProperty(value = "博客前台内容显示")
	private String description;// 描述
	@ApiModelProperty(value = "博客浏览次数",example = "0")
	private Integer views=0;// 浏览次数
	@ApiModelProperty(value = "博客更新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;//
	private Integer typeId;//
	private String typeName;//
	private String tagIds;
	private List<String> tags = new ArrayList<String>();// es博客标签
	private String firstPicture;
}
