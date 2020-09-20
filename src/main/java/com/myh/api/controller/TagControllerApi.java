package com.myh.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myh.service.TypeService;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.service.impl.EsServiceImpl;
import com.myh.service.impl.TagServiceImpl;

/*
**2020年9月16日--下午4:10:49
**@version:
**莫耀华:
**@Description:
*/
@RestController
@RequestMapping("/api/v1/tag")
public class TagControllerApi {
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
}
