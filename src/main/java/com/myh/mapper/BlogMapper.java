package com.myh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.myh.pojo.Blog;
import com.myh.vo.VoBlog;

//@CacheNamespace
@Mapper
public interface BlogMapper {
	
	/************************后台*******************************/
	public int addBlog(Blog blog);
	
	public int delBlof(@Param("id") Long id);
	
	public Blog select(@Param("id")  Long id);
	
	public int updateBlogById(Blog blog);
	
	public List<Blog> selectBlogList();
	
	public List<Blog> selectBlogListLimit(Map<String, Integer> map);
	
	public Integer countBlog();
	
	public Integer countBlogLike(Map<String, Object> map);
	
	public List<Blog> selectBlogListLimitLike(Map<String, Object> map);
	
	public List<Blog> selectRecommend(Map<String, Object> map);
	
	public List<VoBlog> selectFrontDeskBlog(Map<String, Object> map);
	
	
	
	@Update("update t_blog set views=views+1 where id=#{id}")
	public int addViewById(@Param("id") Long id);
	
	/************************后台*******************************/
	/*************************前台*******************************/
	public int countBlogFrontDesk();
	
	//前台最受欢迎
	public List<VoBlog> selectMostPopularBlog();
	
	public int countBlogByType(@Param("id") Long typeId);
	
	public List<VoBlog> selectFBlogByTagId(Map<String, Object> map);
	/*************************前台*******************************/
	
	/************************归档*******************************/
	@Select("SELECT DISTINCT DATE_FORMAT(create_time,'%Y') as update_time from t_blog where publicshed=1 ORDER by update_time desc ")
	public List<String> selectArchiesYearTime();
	
	@Select("SELECT id,title,create_time,flag from t_blog where publicshed=1 and DATE_FORMAT(create_time,'%Y')=#{time}")
	public  List<Blog> selectArchiesByYearTime(String time);
	
	//计算博客数量  只差发布的
	@Select("select count(1) from t_blog where publicshed=1") 
	public int countBlogBypublicshed();
	/************************归档*******************************/
}
