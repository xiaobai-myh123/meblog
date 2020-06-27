package com.myh.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.myh.pojo.Type;
import com.myh.vo.VoType;

/*
**2020年6月8日--上午11:26:23
**@version:
**莫耀华:
**@Description:
*/
public interface TypeService {
	
	public int  addTypeOne( String TypeName);
	
	public void  delTypeOne(int id);
	
	public int  updateTypeOne(int id,String TypeName);
	
	public Type  selectTypeOne(int id);
	
	public List<Type> selectTypeList();
	
	public Type selectTypeByTypeName(String typeName);
	
	public int saveType(Type type);
	
	public List<Type> selectTypeListLimit(Integer currentPageNo, Integer pageSize);
	
	public int countType();
	
	public List<VoType> selectVoTypeListLimit(Long count);
	
	public List<VoType> countTypeByBlog();
}
