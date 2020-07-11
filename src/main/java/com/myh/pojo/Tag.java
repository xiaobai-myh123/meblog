package com.myh.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 	标签
 * @author 莫耀华
 *
 */
@Data
@AllArgsConstructor	
@NoArgsConstructor
@ApiModel(value = "标签实体类",description = "标签信息描述类")
public class Tag implements Serializable{
	
	@ApiModelProperty(value = "标签的id",example = "0")
	private Long id;
	
	@ApiModelProperty(value = "标签的名字")
	@NotBlank(message = "标签不能空>后端")
	private String name;
	
	private List<Blog> blogs=new ArrayList<Blog>();
}
