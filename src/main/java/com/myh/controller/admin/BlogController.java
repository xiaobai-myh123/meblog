package com.myh.controller.admin;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myh.pojo.Blog;
import com.myh.pojo.Type;
import com.myh.pojo.User;
import com.myh.service.TypeService;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.service.impl.TagServiceImpl;
import com.myh.utils.IdWorker;
import com.myh.utils.MyUtils;
import com.myh.utils.PageSupport;
import com.myh.utils.SystemConstant;
import com.myh.vo.BlogQuery;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "后台-博客的数据接口")
@Controller
@RequestMapping("/admin")
@Transactional
public class BlogController {
	
	@Autowired
	private BlogServiceImpl blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagServiceImpl tagService;
	
	//去blog管理博客页面
	@ApiOperation(value = "去blog管理博客页面",notes = "根据页数查看当前页的博客")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType = "path",name = "currentPage",value = "当前页数",required = false),
		@ApiImplicitParam(name = "model",value = "转发的Model",required = false)
	})
	@GetMapping("/blogs")
	public String blogs(
			@PathVariable(value = "currentPage", required = false) String currentPage, 
			Model model
	) { 
		if (currentPage == null) {
			currentPage = "1";
		}
		Integer _currentPage=Integer.parseInt(currentPage);
		model.addAttribute("types", typeService.selectTypeList()); //初始化分类
		//博客
		List<Blog> blogs = 
				blogService.selectBlogListLimit((_currentPage-1)*SystemConstant.NUMBER_OF_PAGES, SystemConstant.NUMBER_OF_PAGES);
		model.addAttribute("blogs", blogs);
		System.out.println(blogs);
		//当前第几页
	  	model.addAttribute("currentPage", _currentPage);//当前页码
		PageSupport pageSupport=new PageSupport();
		pageSupport.setPageSize(SystemConstant.NUMBER_OF_PAGES);
		Integer countBlog = blogService.countBlog();//博客数量
		pageSupport.setTotalCount(countBlog);
		int totalPageCount = pageSupport.getTotalPageCount();
		model.addAttribute("totalPages", totalPageCount);//当前总页数
		return "admin/blogs";
	}
	//分页博客查询
	@ApiOperation(value = "去blog管理博客页面",notes = "分页博客查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id",value = "用户id",defaultValue = "00"),
			@ApiImplicitParam(name = "currentPage",value = "当前页数",required = false),
			@ApiImplicitParam(name = "blog",value = "BlogQuery根据博客的信息查询博客",required = true),
			@ApiImplicitParam(name = "model",value = "转发的Model",required = false)
			})
	@PostMapping("/blogs/search")
	public String blogsSearch(
			String currentPage, 
			BlogQuery blog,
			Model model
	) { 
		System.out.println(blog);
		System.out.println(currentPage);
		if (currentPage == null) {
			currentPage = "1";
		}
		Integer _currentPage=Integer.parseInt(currentPage);
		//分类
		model.addAttribute("types", typeService.selectTypeList());//初始化分类
		//博客
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("currentPageNo", (_currentPage-1)*SystemConstant.NUMBER_OF_PAGES);
		map.put("pageSize", SystemConstant.NUMBER_OF_PAGES);
		map.put("title", blog.getTitle());
		map.put("typeId", blog.getTypeId());
		map.put("recommend", blog.isRecommend());
		map.put("publicshed", blog.isPublicshed());
		List<Blog> blogs =blogService.selectBlogListLimitLike(map);
		model.addAttribute("blogs", blogs);
		//当前第几页
	  	model.addAttribute("currentPage", _currentPage);//当前页码
		PageSupport pageSupport=new PageSupport(); //分页对象
		pageSupport.setPageSize(SystemConstant.NUMBER_OF_PAGES); //设置当前显示页面显示数量
		HashMap<String, Object> mapCount = new HashMap<String, Object>();
		mapCount.put("title", blog.getTitle());
		mapCount.put("typeId", blog.getTypeId());
		mapCount.put("recommend", blog.isRecommend());
		mapCount.put("publicshed", blog.isPublicshed());
		Integer countBlog = blogService.countBlogLike(mapCount);//博客数量
		pageSupport.setTotalCount(countBlog);
		int totalPageCount = pageSupport.getTotalPageCount();
		model.addAttribute("totalPages", totalPageCount);//当前总页数
		return "admin/blogs::blogList";//返回片段
	}
	//去博客新增页面
	@ApiOperation(value = "去博客新增页面",notes = "去博客新增页面")
	@ApiImplicitParam(name = "model",value = "转发的Model",required = false)
	@GetMapping("/blogs/input") 
	public String input(Model model) {
		model.addAttribute("blog", new Blog());
		model.addAttribute("types", typeService.selectTypeList());//初始化分类
		model.addAttribute("tags", tagService.selectTagList());//初始化标签
		return "admin/blogs-input";
	}
	
	//新增博客或者 修改博客   
	@ApiOperation(value = "新增博客或者 修改博客",notes = "根据id修改博客或者直接增加博客")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "用户id",defaultValue = "00"),
		@ApiImplicitParam(name = "session",value = "HttpSession",required = false),
		@ApiImplicitParam(name = "blog",value = "Blog blog的信息",required = true),
		@ApiImplicitParam(name = "attributes",value = "RedirectAttributes重定向的类",required = false),
		@ApiImplicitParam(name = "model",value = "转发的Model",required = false)
		})
	@PostMapping("/addBlogs")
	public String post(
			HttpSession session,
			Blog blog,
			RedirectAttributes attributes,
			Model model
	) {
		blog.setUser((User)session.getAttribute("user"));//用户信息
		//根据分类id查询分类
		Type selectTypeOne = typeService.selectTypeOne(Integer.parseInt(blog.getType().getId().toString()));
		blog.setType(selectTypeOne);
//		System.out.println(blog.getDescription());
		blog.setTags(tagService.listByIdsTag(blog.getTagIds()));
		if(blog.getId()==null) { 
			//博客的描述  就是现在前端的内容 
			long nextId = IdWorker.nextId();//博客id 雪花算法 随机生成
			blog.setId(nextId);
			int addBlog = blogService.addBlog(blog);//保存博客
			tagService.insertByIdsTag(nextId, MyUtils.convertToList(blog.getTagIds()));//保存标签
//			System.out.println(blog.getId());
			if(addBlog>0) {
				attributes.addFlashAttribute("message", "添加博客成功");
			}else {
				attributes.addFlashAttribute("message", "添加博客失败");
			}
		}else { 
			int updateBlogById = blogService.updateBlogById(blog);//修改博客 
			tagService.delTagOne(blog.getId());//先删出标签
			tagService.insertByIdsTag(blog.getId(), MyUtils.convertToList(blog.getTagIds()));//保存标签
			if(updateBlogById>0) {
				attributes.addFlashAttribute("message", "修改博客成功");
			}else {
				attributes.addFlashAttribute("message", "修改博客失败");
			}
		}
		return "redirect:/admin/blogs";
		
	}
	//去修改
	@ApiOperation(value = "去修改博客页面",notes = "根据博客id查询博客")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "博客id",required = false,defaultValue = "00"),
		@ApiImplicitParam(name = "model",value = "转发的Model",required = false)
		})
	@GetMapping("/blogs/{id}/input") 
	public String editInput(@PathVariable("id") Long id,Model model) {
		Blog blog = blogService.select(id);
		if(blog==null) {
			System.out.println("editInput 查询博客为空 id异常");
		}
		model.addAttribute("blog", blog);
		model.addAttribute("types", typeService.selectTypeList());//初始化分类
		model.addAttribute("tags", tagService.selectTagList());//初始化标签
		return "admin/blogs-input";
	}
	//删除博客
	@ApiOperation(value = "删除博客",notes = "根据博客id删除博客")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id",value = "博客id",required = false,defaultValue = "00"),
		@ApiImplicitParam(name = "model",value = "转发的Model",required = false)
		})
	@GetMapping("/blogs/{id}/delete") 
	public String delete(
			@PathVariable("id") Long id,
			RedirectAttributes attributes
	) {
		tagService.delTagOne(id);//先删出标签 根据博客id
		int delBlof = blogService.delBlof(id);
		if(delBlof>0) {
			attributes.addFlashAttribute("message", "删除成功");
		}else {
			attributes.addFlashAttribute("message", "删除失败");
		}
		return "redirect:/admin/blogs";
	}
}/*
appreciation
Blog(
id=null, 
title=1, 
content=1, 
firstPicture=https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2936173286,2013830415&fm=26&gp=0.jpg, 
flag=, 
description=null, 
views=0, 
appreciation=true, 
sharStatement=true, 
commentabled=true, 
publicshed=true, 
recommend=true, 
createTime=null, 
updateTime=null, 
tagIds=2,3, 
type=Type(id=3, name=null, blogs=[]), 
tags=[], 
user=null,
comments=[])

Blog(
id=null, 
title=2, 
content=1, 
firstPicture=https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2936173286,2013830415&fm=26&gp=0.jpg, 
flag=翻译, 
description=null, 
views=0, 
appreciation=true, 
sharStatement=true, 
commentabled=true, 
publicshed=false, 
recommend=true, 
createTime=null, 
updateTime=null, 
tagIds=2,3, 
type=Type(id=1, name=null, blogs=[]), 
tags=[], 
user=null, 
comments=[])
*/