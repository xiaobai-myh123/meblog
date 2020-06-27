package com.myh.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myh.pojo.Blog;
import com.myh.vo.VoBlog;

/*
**2020年6月10日--下午10:48:20
**@version:
**莫耀华:
**@Description:
*/
public interface BlogService {
	
	public int addBlog(Blog blog);
	
	public int delBlof(Long id);
	
	public Blog select(Long id);
	
	public int updateBlogById(Blog blog);
	
	public List<Blog> selectBlogList();
	
	public List<Blog> selectBlogListLimit(Integer currentPage,Integer pageSize);
	
	public int countBlog();
	
	public List<Blog> selectBlogListLimitLike(Map<String, Object> map);
	
	public Integer countBlogLike(Map<String, Object> map);
	
	public List<Blog> selectRecommend(Integer currentPage,Integer pageSize);
	
	public List<VoBlog> selectVoFrontDeskBlog(Integer currentPage,Integer pageSize,Long typeId);
	
	public int countBlogFrontDesk();
	
	public List<VoBlog> selectMostPopularBlog();
	
	public int countBlogByType(@Param("id")  Long typeId);
	
	public List<VoBlog> selectFBlogByTagId(Integer currentPage,Integer pageSize,Long tagId);
	
	public List<String> selectArchiesYearTime();
	 
	public List<Blog> selectArchiesByYearTime(String time);
	
	public int addViewById(@Param("id") Long id);
	
	public int countBlogBypublicshed();
}
