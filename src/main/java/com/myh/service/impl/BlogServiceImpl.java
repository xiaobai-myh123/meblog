package com.myh.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myh.mapper.BlogMapper;
import com.myh.mapper.TagMapper;
import com.myh.pojo.Blog;
import com.myh.pojo.ESBlog;
import com.myh.pojo.Tag;
import com.myh.service.BlogService;
import com.myh.utils.MarkdownUtils;
import com.myh.vo.VoBlog;

/*
**2020年6月10日--下午10:49:04
**@version:
**莫耀华:
**@Description:
*/
@Service
@Transactional
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	private BlogMapper blogMapper;
	
	@Autowired
	private TagMapper tagMapper;
	
	//增加一个博客  还有增加一个es博客对象
	@Override
	public int addBlog(Blog blog) {
		blog.setCreateTime(new Date());
		blog.setUpdateTime(new Date());
		blog.setDescription(MarkdownUtils.convert(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()).substring(0, blog.getContent().length()>160?160:blog.getContent().length())));
		return blogMapper.addBlog(blog);
	}

	@Override
	public Blog select(Long id) {
		Blog blog = blogMapper.select(id);
		String tagIds = blog.getTagIds();
		List<Long> list = convertToList(tagIds);
		List<Tag> tagList = tagMapper.listByIdsTag(list);
		blog.setTags(tagList);
		return blog;
	}

	@Override
	public int updateBlogById(Blog blog) {
		blog.setDescription(MarkdownUtils.convert(MarkdownUtils.markdownToHtmlExtensions(blog.getContent()).substring(0, blog.getContent().length()>160?160:blog.getContent().length())));
		blog.setUpdateTime(new Date());
		return blogMapper.updateBlogById(blog);
	} 

	@Override
	public List<Blog> selectBlogList() {
		return blogMapper.selectBlogList();
	}

	@Override
	public List<Blog> selectBlogListLimit(Integer currentPageNo,Integer pageSize) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("currentPageNo", currentPageNo);
		map.put("pageSize", pageSize);
		return blogMapper.selectBlogListLimit(map);
	}

	@Override
	public int countBlog() {
		return blogMapper.countBlog();
	}
    /*将字符串转化为集合*/
	private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

	@Override
	public int delBlof(Long id) {
		return blogMapper.delBlof(id);
	}

	@Override
	public List<Blog> selectBlogListLimitLike(Map<String, Object> map) {
		return blogMapper.selectBlogListLimitLike(map);
	}

	@Override
	public Integer countBlogLike(Map<String, Object> map) {
		return blogMapper.countBlogLike(map);
	}

	@Override
	public List<Blog> selectRecommend(Integer currentPageNo, Integer pageSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPageNo", currentPageNo);
		map.put("pageSize", pageSize);
		return blogMapper.selectRecommend(map);
	}

	@Override
	public List<VoBlog> selectVoFrontDeskBlog(Integer currentPageNo, Integer pageSize,Long typeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPageNo", currentPageNo);
		map.put("pageSize", pageSize);
		map.put("typeId", typeId);
		return blogMapper.selectFrontDeskBlog(map);
	}

	@Override
	public int countBlogFrontDesk() {
		return blogMapper.countBlogFrontDesk();
	}

	@Override
	public List<VoBlog> selectMostPopularBlog() {
		return blogMapper.selectMostPopularBlog();
	}

	@Override
	public int countBlogByType(Long typeId) {
		// TODO Auto-generated method stub
		return blogMapper.countBlogByType(typeId);
	}

	@Override
	public List<VoBlog> selectFBlogByTagId(Integer currentPage, Integer pageSize, Long tagId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("currentPageNo", currentPage);
		map.put("pageSize", pageSize);
		map.put("tagId", tagId);
		return blogMapper.selectFBlogByTagId(map);
	}
	
	public List<VoBlog> selectFBlogByTagIdAdvice(Integer currentPage, Integer pageSize, Long tagId) {
		List<VoBlog> voBlog = selectFBlogByTagId(currentPage, pageSize, tagId);
		for (int i = 0; i < voBlog.size(); i++) {
			VoBlog blog = voBlog.get(i);
			String ids = blog.getTagIds();
			List<Long> list = convertToList(ids);
			List<Tag> tagList = tagMapper.listByIdsTag(list);
			voBlog.get(i).setTags(tagList);
		}
		return voBlog;
	}

	@Override
	public List<String> selectArchiesYearTime() {
		return blogMapper.selectArchiesYearTime();
	} 

	@Override
	public List<Blog> selectArchiesByYearTime(String time) {
		return blogMapper.selectArchiesByYearTime(time);
	}
	
	public Map<String,List<Blog>> selectArchiesByYearTimeAdvice() {
		Map<String, List<Blog>> map = new HashMap<String, List<Blog>>();
		List<String> list = selectArchiesYearTime();
		for (String year : list) {
			map.put(year, selectArchiesByYearTime(year));
		}
		return map;
	}

	@Override
	public int countBlogBypublicshed() {
		return blogMapper.countBlogBypublicshed();
	}

	@Override
	public int addViewById(Long id) {
		return blogMapper.addViewById(id);
	}

	@Override
	public List<ESBlog> getListEsBlog() {
		return blogMapper.getListEsBlog();
	}
	/****
	 * api模块   使用分页插件
	 */

	@Override
	public List<Blog> selectBlogListApi(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return blogMapper.selectBlogListApi(map);
	}
}
