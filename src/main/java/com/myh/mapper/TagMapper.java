package com.myh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myh.pojo.Tag;
import com.myh.vo.VoTag;



@Mapper
//@CacheNamespace
public interface TagMapper {
	
	public int  addTagOne(@Param("tageName") String tageName);
	
	public int  delTagOne(@Param("id") Long id);
	
	public int  updateTagOne(@Param("id") int id,@Param("tageName") String tageName);
	
	public Tag  selectTagOne(@Param("id") int id);
	
	public List<Tag> selectTagList();
	
	public Tag selectTagByTagName(@Param("tageName")String tageName);
	
	public int saveTag(Tag tag);
	
	public List<Tag> selectTagListLimit(Map<String, Object> map);
	
	public int countTag();
	
	public List<Tag> listByIdsTag(List<Long> idsList);
	
	public int insertByIdsTag(@Param("blogs_id") Long blogs_id,List<Long> list);
	
	public List<VoTag> selectVoTagListLimit(Long count); 
	
	public int countBlogByTagId(@Param("tags_id") int tags_id);
	
	@Delete("delete from t_tag where id=#{id}")
	public int delTagByTagId(@Param("id") Long tags_id);
}
