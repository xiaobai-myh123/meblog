package com.myh.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myh.mapper.TypeMapper;
import com.myh.pojo.Type;
import com.myh.service.TypeService;
import com.myh.vo.VoType;

@Service
@Transactional
public class TypeServiceImpl implements TypeService{
	
	@Autowired
	private TypeMapper typeMapper;
	
	@Override
	public int addTypeOne(String TypeName) {
		return typeMapper.addTypeOne(TypeName);
	}

	@Override
	public void delTypeOne(int id) {
		typeMapper.delTypeOne(id);
	}

	@Override
	public int updateTypeOne(int id, String TypeName) {
		return typeMapper.updateTypeOne(id, TypeName);
	}

	@Override
	public Type selectTypeOne(int id) {
		return typeMapper.selectTypeOne(id);
	}

	@Override
	public List<Type> selectTypeList() {
		return typeMapper.selectTypeList();
	}

	@Override
	public Type selectTypeByTypeName(String typeName) {
		return typeMapper.selectTypeByTypeName(typeName);
	}

	@Override
	public int saveType(Type type) {
		return typeMapper.saveType(type);
	}

	@Override
	public List<Type> selectTypeListLimit(Integer currentPageNo, Integer pageSize) {
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("currentPageNo", currentPageNo);
		map.put("pageSize", pageSize);
		return typeMapper.selectTypeListLimit(map);
	}

	@Override
	public int countType() {
		return typeMapper.countType();
	}

	@Override
	public List<VoType> selectVoTypeListLimit(Long count) {
		return typeMapper.selectVoTypeListLimit(count);
	}

	@Override
	public List<VoType> countTypeByBlog(){
		// TODO Auto-generated method stub
		return typeMapper.countTypeByBlog();
	}

}
