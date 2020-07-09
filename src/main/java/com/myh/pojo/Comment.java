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
 * 评论
 * @author 莫耀华
 *
 */
@Data
@AllArgsConstructor	
@NoArgsConstructor
@ApiModel("评论实体类")
public class Comment implements Serializable{
	
	@ApiModelProperty(value = "评论的id",example = "0")
	private Long id;
	@ApiModelProperty(value = "评论的名字")
	private String name;
	@ApiModelProperty(value = "评论的email")
	private String email;
	@ApiModelProperty(value = "评论的内容")
	private String content;
	@ApiModelProperty(value = "评论首图 有默认值")
	private String avatar;//头像评论地址
	//是否为博主
	@ApiModelProperty(value = "评论是否为博主")
    private boolean adminComment;
	
	@ApiModelProperty(value = "评论创建时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	private Blog blog;
	
	//自己关联
	private List<Comment> replyComments=new ArrayList<Comment>();//子评论
	private Comment parentConmment;//父评论
	private Long parentConmmentId;
	
}
