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
import com.myh.pojo.Tag;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.service.impl.TagServiceImpl;
import com.myh.utils.PageSupport;
import com.myh.utils.SystemConstant;
import com.myh.vo.VoBlog;
import com.myh.vo.VoTag;

/*
**2020年6月23日--下午2:26:39
**@version:
**莫耀华:
**@Description:
*/
@Controller
public class TagShowController {
    @Autowired
    private TagServiceImpl tagServiceImpl;


    @Autowired
    private BlogServiceImpl blogServiceImpl;

    //去标签首页
	@GetMapping("/tags/{id}")
	public String tags(@PathVariable("id") Long id,
			@RequestParam(value = "currentPage", required = false) String _currentPage, Model model) {
		if (_currentPage == null) {
			_currentPage = "1";
		}
		Integer currentPage = Integer.parseInt(_currentPage);
		//3. 获取标签的内容
		List<VoTag> tags = tagServiceImpl.selectVoTagListLimit(null);
		
		//如果标签为空  默认选择最多的标签展示
		if (tags.size() == 0 && id == 0) {
			throw new NotFoundException("分类为空 抛出异常");
		}
		if (id == 0) {
			id = tags.get(0).getId();
		}
		Tag tag = tagServiceImpl.selectTagOne(Integer.parseInt(id+""));
		if (tag==null) {
			throw new NotFoundException("TagShowController 分类为空 抛出异常");
		}
		// 当前第几页
		model.addAttribute("currentPage", currentPage);
		// 被选中分类
		model.addAttribute("avtiveTypeId", id);
		// 所有的标签
		model.addAttribute("tags", tags);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		
		//查询的博客
		List<VoBlog> blogs = 
				blogServiceImpl.selectFBlogByTagIdAdvice((currentPage-1)*SystemConstant.RECOMMEND_OF_PAGES, SystemConstant.RECOMMEND_OF_PAGES,id);
		// 当前分类的总博客数
		model.addAttribute("blogs", blogs);
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(SystemConstant.RECOMMEND_OF_PAGES);
		pageSupport.setTotalCount(tagServiceImpl.countBlogByTagId(Integer.parseInt(id+"")));
		// 总页数
		model.addAttribute("totalPages", pageSupport.getTotalPageCount());
		
//		System.out.println("TotalCount="+tagServiceImpl.countBlogByTagId(Integer.parseInt(id+"")));
//		System.out.println("totalPages="+pageSupport.getTotalPageCount());
//		System.out.println("currentPage="+currentPage);
		return "tags";
	}
    //去标签首页 分页
	@RequestMapping("/tags/limitIndex")
	public String pageLimittags(
			@RequestParam("id") String  _id,
			@RequestParam(value = "currentPage", required = false) String _currentPage, 
			Model model) {
		if (_currentPage == null) {
			_currentPage = "1";
		}
		Long id=Long.parseLong(_id);
		Integer currentPage = Integer.parseInt(_currentPage);
		//3. 获取标签的内容
		List<VoTag> tags = tagServiceImpl.selectVoTagListLimit(null);
		
		//如果标签为空  默认选择最多的标签展示
		if (tags.size() == 0 && id == 0) {
			throw new NotFoundException("分类为空 抛出异常");
		}
		if (id == 0) {
			id = tags.get(0).getId();
		}
		Tag tag = tagServiceImpl.selectTagOne(Integer.parseInt(id+""));
		if (tag==null) {
			throw new NotFoundException("TagShowController 分类为空 抛出异常");
		}
		// 当前第几页
		model.addAttribute("currentPage", currentPage);
		// 被选中分类
		model.addAttribute("avtiveTypeId", id);
		// 所有的标签
		model.addAttribute("tags", tags);
		// 最受欢迎
		model.addAttribute("newblogs", blogServiceImpl.selectMostPopularBlog());
		
		//查询的博客
		List<VoBlog> blogs = 
				blogServiceImpl.selectFBlogByTagIdAdvice((currentPage-1)*SystemConstant.RECOMMEND_OF_PAGES, SystemConstant.RECOMMEND_OF_PAGES,id);
		// 当前分类的总博客数
		model.addAttribute("blogs", blogs);
		PageSupport pageSupport = new PageSupport();
		pageSupport.setPageSize(SystemConstant.RECOMMEND_OF_PAGES);
		pageSupport.setTotalCount(tagServiceImpl.countBlogByTagId(Integer.parseInt(id+"")));
		// 总页数
		model.addAttribute("totalPages", pageSupport.getTotalPageCount());
//		System.out.println("TotalCount="+tagServiceImpl.countBlogByTagId(Integer.parseInt(id+"")));
//		System.out.println("totalPages="+pageSupport.getTotalPageCount());
//		System.out.println("currentPage="+currentPage);
		return "tags::indexList";
	}
}
