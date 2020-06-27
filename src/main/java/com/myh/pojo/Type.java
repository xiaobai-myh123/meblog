package com.myh.pojo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
	
/**
 * 博客分类
 * @author 莫耀华
 *
 */


@Data
@AllArgsConstructor	
@NoArgsConstructor
public class Type implements Serializable{
	
	private Long id;	
	private String name;
	
	private List<Blog> blogs=new ArrayList<Blog>();
	
}
