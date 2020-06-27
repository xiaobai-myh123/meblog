package com.myh.vo;
/*
**2020年6月13日--下午6:11:09
**@version:
**莫耀华:
**@Description: 前台显示 分类的bean
*/


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor	
@NoArgsConstructor
public class VoType implements Serializable{
	private Long id;	
	private String name;
	private Long count;
}
