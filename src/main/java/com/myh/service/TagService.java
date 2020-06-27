package com.myh.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myh.pojo.Tag;
import com.myh.vo.VoTag;

/*
**2020年6月10日--下午6:13:52
**@version:
**莫耀华:
**@Description:
*/
public interface TagService {
	public int  addTagOne(@Param("tageName") String tageName);
	
	public int  delTagOne(@Param("id") Long id);
	
	public int  updateTagOne(@Param("id") int id,@Param("tageName") String tageName);
	
	public Tag  selectTagOne(@Param("id") int id);
	
	public List<Tag> selectTagList();
	
	public Tag selectTagByTagName(@Param("tageName")String tageName);
	
	public int saveTag(Tag Tag);
	
	public List<Tag> selectTagListLimit(Integer currentPageNo, Integer pageSize);
	
	public int countTag();
	
	public List<Tag> listByIdsTag(String idsList);
	
	public int insertByIdsTag(Long blogs_id,List<Long> idsList);
	
	public List<VoTag> selectVoTagListLimit(Long count); 
	
	public int countBlogByTagId(@Param("tags_id") int tags_id);
	
	public int delTagByTagId(@Param("id") Long tags_id);
}
