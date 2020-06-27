package com.myh.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor	
@NoArgsConstructor
public class BlogQuery implements Serializable{
	
	private String title;
	private Long typeId;
	private boolean recommend;
	private boolean publicshed;
}
