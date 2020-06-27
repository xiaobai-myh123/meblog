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
 * 评论
 * @author 莫耀华
 *
 */
@Data
@AllArgsConstructor	
@NoArgsConstructor
public class Comment implements Serializable{

	private Long id;
	private String name;
	private String email;
	private String content;
	private String avatar;//头像评论地址
	//是否为博主
    private boolean adminComment;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	private Blog blog;
	
	//自己关联
	private List<Comment> replyComments=new ArrayList<Comment>();//子评论
	private Comment parentConmment;//父评论
	private Long parentConmmentId;
	
}
