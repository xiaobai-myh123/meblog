package com.myh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.myh.pojo.Blog;
import com.myh.service.impl.BlogServiceImpl;
import com.myh.utils.SystemConstant;
import com.myh.vo.VoBlog;

/*
**2020年6月10日--下午6:17:30
**@version:
**莫耀华:
**@Description:
*/
@SpringBootTest
public class BlogTest {

	@Autowired
	private BlogServiceImpl  blogServiceImpl;


	@Test
	void selectBlogListLimit() {
		System.out.println(blogServiceImpl.select(1271646382477803520L));
	}
	
	@Test
	void selectRecommend() {
		List<Blog> selectRecommend = blogServiceImpl.selectRecommend(0, 5);
	}
	@Test
	void selectMostPopularBlog() {
		List<VoBlog> selectMostPopularBlog = blogServiceImpl.selectMostPopularBlog();
		selectMostPopularBlog.forEach(System.out::println);
	}
	
	@Test
	void selectByTypeId() {
//		int i = blogServiceImpl.countBlogByType(3L);
//		System.out.println(i);
		// 根据分类显示博客
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("typeId", 3);
		map.put("pageSize", SystemConstant.RECOMMEND_OF_PAGES);
		map.put("currentPageNo", 0 * SystemConstant.RECOMMEND_OF_PAGES);
		List<Blog> blogs = blogServiceImpl.selectBlogListLimitLike(map);
		System.out.println(blogs);
	}
	
	@Test
	void selectArchiesYearTime() {
		List<String> list = blogServiceImpl.selectArchiesYearTime();
		list.forEach(System.out::println);
	}
	
//	@Test
//	void selectArchiesYearTimeMap() {
//		List<Map<String, Object>> list = blogServiceImpl.selectArchiesYearTimeMap();
//		list.forEach(System.out::println);
//	}
	
	@Test
	void selectArchiesYearTimeMap() {
		List<Blog> list = blogServiceImpl.selectArchiesByYearTime("2017");
		list.forEach(System.out::println);
	}
	@Test
	void selectArchiesByYearTimeAdvice() {
		Map<String, List<Blog>> map = blogServiceImpl.selectArchiesByYearTimeAdvice();
		map.entrySet().forEach(System.out::println);
	}
}
