package com.myh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.myh.NotFoundException;
import com.myh.pojo.Type;
import com.myh.service.BlogService;
import com.myh.service.TypeService;
import com.myh.utils.PageSupport;
import com.myh.utils.SystemConstant;
import com.myh.vo.VoBlog;
import com.myh.vo.VoType;

/*
**2020年6月22日--下午8:54:47
**@version:
**莫耀华:
**@Description:
*/
@Controller
public class TypeShowController {
	@Autowired
	private TypeService typeServiceImpl;

	@Autowired
	private BlogService blogServiceImpl;

	@GetMapping("/types/{id}")
	public String types(
			@PathVariable("id") Long id,
			@RequestParam(value = "currentPage", required = false) String _currentPage, 
			Model model) {
		if (_currentPage == null) {
			_currentPage = "1";
		}
		Integer currentPage = Integer.parseInt(_currentPage);
		List<VoType> types = typeServiceImpl.countTypeByBlog();
		if (types.size() == 0 && id == 0) {
			throw new NotFoundException("分类为空 抛出异常");
		}
		if (id == 0) {
			id = types.get(0).getId();
		}
		Type type = typeServiceImpl.selectTypeOne(Integer.parseInt(id+""));
		if(type==null) {
			throw new NotFoundException("出错了！");
		}
		// 当前第几页
		// 根据分类显示博客
		List<VoBlog> blogs = blogServiceImpl.selectVoFrontDeskBlog(
				(currentPage - 1) * SystemConstant.RECOMMEND_OF_PAGES, SystemConstant.RECOMMEND_OF_PAGES, id);
		// 当前标签的总博客数
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(SystemConstant.RECOMMEND_OF_PAGES);
		pageSupport.setTotalCount(blogServiceImpl.countBlogByType(id));

		// 当前第几页
		model.addAttribute("currentPage", currentPage);
		// 根据标签查询的博客
		model.addAttribute("blogs", blogs);
		// 总页数
		model.addAttribute("totalPages", pageSupport.getTotalPageCount());
		// 被选中分类
		model.addAttribute("avtiveTypeId", id);
		// 所有的分类
		model.addAttribute("types", types);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		return "types";
	}

	@RequestMapping(value = {"/types/limitIndex/"})
	public String typesLimit(
			@RequestParam("id") String  _id,
			@RequestParam(value = "currentPage", required = false) String _currentPage,
			Model model) {
		if (_currentPage == null) {
			_currentPage = "1";
		}
		System.out.println(_id);
		Long id=Long.parseLong(_id);
		System.out.println(id);
		if(id<0) {
			throw new NotFoundException();
		}
		Integer currentPage = Integer.parseInt(_currentPage);
		List<VoType> types = typeServiceImpl.countTypeByBlog();
		if (types.size() == 0 && id == 0) {
			throw new NotFoundException("TypeShowController 分类为空 抛出异常");
		}
		if (id == 0) {
			id = types.get(0).getId();
		}
		// 当前第几页
		// 根据分类显示博客
		List<VoBlog> blogs = blogServiceImpl.selectVoFrontDeskBlog(
				(currentPage - 1) * SystemConstant.RECOMMEND_OF_PAGES, SystemConstant.RECOMMEND_OF_PAGES, id);
		System.out.println(blogs.size());
		// 当前标签的总博客数
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(SystemConstant.RECOMMEND_OF_PAGES);
		pageSupport.setTotalCount(blogServiceImpl.countBlogByType(id));
		// 当前第几页
		model.addAttribute("currentPage", currentPage);
		// 根据标签查询的博客
		model.addAttribute("blogs", blogs);
		// 总页数
		model.addAttribute("totalPages", pageSupport.getTotalPageCount());
		// 被选中分类
		model.addAttribute("avtiveTypeId", id);
		// 所有的分类
		model.addAttribute("types", types);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		Type type = typeServiceImpl.selectTypeOne(Integer.parseInt(id+""));
		if(type==null) {
			throw new NotFoundException("TypeShowController 分页！");
		}
		return "types::indexList";
	}

}
