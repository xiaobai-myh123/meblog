package com.myh.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.myh.api.pojo.PageUtils;
import com.myh.api.pojo.Result;
import com.myh.pojo.Blog;
import com.myh.service.TypeService;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.service.impl.EsServiceImpl;
import com.myh.service.impl.TagServiceImpl;
import com.myh.vo.BlogQuery;

/*
**2020年9月14日--下午9:12:04
**@version:
**莫耀华:
**@Description:
*/
@RestController
@RequestMapping("/api/v1/blog")
public class BlogControllerApi {
	@Autowired
	private BlogServiceImpl blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagServiceImpl tagService;
	@Autowired
	private EsServiceImpl esServiceImpl;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/getBlogList", produces = "application/json;charset=UTF-8")
	public Result getBlogs(Integer currentPage, Integer pageSize, BlogQuery blog) {
		try {
			System.out.println(blog);
			System.out.println(currentPage);
			System.out.println(pageSize);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("title", blog.getTitle());
			map.put("typeId", blog.getTypeId());
			map.put("recommend", blog.isRecommend());
			map.put("publicshed", blog.isPublicshed());
			// 如果传入了参数 就分页 不传返回全部
			if (currentPage != null && pageSize != null) {
				if (currentPage <= 0) {
					currentPage = 1;
				}
				if (pageSize <= 0) {
					pageSize = 5;
				}
				PageHelper.startPage(currentPage, pageSize, true); // PageHelper 只对其后的第一个查询有效
				map.put("currentPageNo", (currentPage - 1) * pageSize);
				map.put("pageSize", pageSize);
				List<Blog> blogs = blogService.selectBlogListApi(map);
				PageInfo info = new PageInfo(blogs);
				currentPage = info.getPages();
				System.out.println(info.getPageSize());
				PageUtils pageUtils = new PageUtils(blogs, Integer.parseInt(info.getTotal() + ""), pageSize,
						currentPage);
				return Result.ok(200, "获取博客数据成功").put("data", pageUtils);
			}
			List<Blog> blogs = blogService.selectBlogListApi(map);
			return Result.ok(200, "获取博客数据成功").put("data", blogs);

		} catch (Exception e) {
			logger.error("错误代码=com.myh.api.controller.BlogControllerApi.getBlogs，{}", e);
			return Result.error(400, "错误原因未知！").put("data", null);
		}

	}

	@PostMapping(value = "/getBlogList1", produces = "application/json")
	public Map getBlogs1(@RequestBody Map<String, Object> map) {
		System.out.println(map.get("currentPage"));
		return map;
	}

}
