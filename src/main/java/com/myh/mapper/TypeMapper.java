package com.myh.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.myh.pojo.Type;
import com.myh.vo.VoType;

@Mapper
//@CacheNamespace
public interface TypeMapper {
	
	public int  addTypeOne(@Param("name") String TypeName);
	
	public void  delTypeOne(@Param("id") int id);
	
	public int  updateTypeOne(@Param("id") int id,@Param("name") String TypeName);
	
	public Type  selectTypeOne(@Param("id") int id);
	
	public List<Type> selectTypeList();
	
	public Type selectTypeByTypeName(@Param("typeName")String typeName);
	
	public int saveType(Type type);
	
	public List<Type> selectTypeListLimit(Map<String, Object> map);
	
	public int countType();
	
	public List<VoType> selectVoTypeListLimit(Long count);
	
	public List<VoType> countTypeByBlog();
}
