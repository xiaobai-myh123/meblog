package com.myh.service;
/*
**2020年7月13日--下午11:33:03
**@version:
**莫耀华:
**@Description:
*/
/**
 * es 的操作 不走数据库  直接走es
 * @author 莫耀华
 *
 */

import java.util.List;
import java.util.Map;

import com.myh.pojo.ESBlog;

public interface EsService {
	//创建索引 （相当于数据库）
	public void createIndex(String indexName);
	//判断索引是否存在
	public boolean isExistsIndex(String indexName);
	//执行搜索操作
	public List<Map<String, Object>> getListAll(String keyword,int pageNo,int pageSize);
	//初始化ES数据数据   只在第一次创建索引时使用
	public void initEsData(String indexName);
	//更新文档
	public int updatedocumentById(Long id);
	//删除文档
	public int deldocumentById(Long id);
	//添加一个文档
	public int savedocumentById(Long id);
}
