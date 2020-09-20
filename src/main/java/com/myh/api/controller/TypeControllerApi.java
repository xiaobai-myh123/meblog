package com.myh.api.controller;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myh.api.pojo.Result;
import com.myh.pojo.Type;
import com.myh.service.TypeService;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.service.impl.EsServiceImpl;
import com.myh.service.impl.TagServiceImpl;

/*
**2020年9月14日--下午10:24:52
**@version:
**莫耀华:
**@Description:
*/
@RestController
@RequestMapping("/api/v1/type")
public class TypeControllerApi {
	@Autowired
	private TypeService typeService;
	@Autowired
	private BlogServiceImpl blogService;
	@Autowired
	private TypeService selectTypeList;
	@Autowired
	private TagServiceImpl tagService;
	@Autowired
	private EsServiceImpl esServiceImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//获取所有的分类列表
	@GetMapping("/getListType")
	public Result getListType(){ 
		List<Type> selectTypeList = typeService.selectTypeList();
		return Result.ok(200,"获取分类数据成功").put("data", selectTypeList);
	}
		
}
