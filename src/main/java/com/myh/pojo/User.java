package com.myh.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor	
@NoArgsConstructor
@ApiModel("用户实体类")
public class User implements Serializable{
	@ApiModelProperty(value = "用户的id",example = "0")
	private Long id;
	@ApiModelProperty(value = "用户的昵称")
	private String nickname;//昵称
	@ApiModelProperty(value = "用户的用户名")
	private String username;//用户名
	@ApiModelProperty(value = "用户的密码")
	private String password;//密码
	@ApiModelProperty(value = "用户的邮箱")
	private String email;//邮箱
	@ApiModelProperty(value = "用户的头像")
	private String avatar;//评论地址
	private Integer type;//类型
	@ApiModelProperty(value = "用户的创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;//
	@ApiModelProperty(value = "用户的更新时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updateTime;//
	
	private List<Blog> blogs=new ArrayList<Blog>();
}
