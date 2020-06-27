package com.myh.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
**2020年6月13日--下午6:21:54
**@version:
**莫耀华:
**@Description: 前台展示 标签bean
*/
@Data
@AllArgsConstructor	
@NoArgsConstructor
public class VoTag implements Serializable{
	private Long id;	
	private String name;
	private Long count;
}
