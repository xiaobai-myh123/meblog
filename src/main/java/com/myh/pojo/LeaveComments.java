package com.myh.pojo;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("留言实体类")
public class LeaveComments implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "留言的id",example = "0")
	private Long id;
	@ApiModelProperty(value = "留言是否为博主")
	private Boolean adminComment;//是否为管理员
	@ApiModelProperty(value = "留言首图 有默认值")
	private String avatar;//默认头像
	@ApiModelProperty(value = "留言的内容")
	private String content;//评论内容
	@ApiModelProperty(value = "留言创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	@ApiModelProperty(value = "留言的email")
	private String name;
	@ApiModelProperty(value = "留言的email")
	private String  email;
}
