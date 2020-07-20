package com.myh.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.myh.mapper.BlogMapper;
import com.myh.mapper.TypeMapper;
import com.myh.pojo.ESBlog;
import com.myh.pojo.Tag;
import com.myh.pojo.Type;
import com.myh.service.BlogService;
import com.myh.service.EsService;
import com.myh.service.TagService;
import com.myh.service.TypeService;
import com.myh.service.UserService;

/*
**2020年7月13日--下午11:34:28
**@version:
**莫耀华:
**@Description:
*/
@Service
public class EsServiceImpl implements EsService {
	@Autowired
	private RestHighLevelClient client;
	@Autowired
	private TagService tagServiceImpl;
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private TypeMapper typeMapper;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	// 创建索引
	@Override
	public void createIndex(String indexName) {
		boolean existsIndex = isExistsIndex(indexName);
		if (!existsIndex) {
			// 不存在索引 则创建索引
			CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
			// 2.客户端执行请求 返回一个IndicesClient 请求后获取响应
			try {
				CreateIndexResponse create = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
				// 创建索引 然后插入数据
				initEsData(indexName);
			} catch (IOException e) {
				logger.info("创建索引异常" + new Date());
				e.printStackTrace();
			}
		}
	}

	// 判断索引是否存在
	@Override
	public boolean isExistsIndex(String indexName) {
		GetIndexRequest getIndexRequest = new GetIndexRequest(indexName);
		boolean exists = false;
		try {
			exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			logger.info("判断索引异常" + new Date());
			e.printStackTrace();
		}
		return exists;
	}

	// 根据条件查询数据
	@Override
	public List<Map<String, Object>> getListAll(String keyword, int pageNo, int pageSize) {
		String indexName = "meblog";
		// 先创建索引 理论上不需要
		createIndex(indexName);
		SearchRequest searchRequest = new SearchRequest(indexName);
		// 条件搜索
		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		// 分页
//		sourceBuilder.from(pageNo);
//		sourceBuilder.size(pageSize);//查询全部
		// 高亮
		HighlightBuilder highlightBuilder = new HighlightBuilder();
		highlightBuilder.field("title");// 高亮的
		highlightBuilder.field("description");// 高亮的
		highlightBuilder.field("tags");// 高亮的
		highlightBuilder.field("typeName");// 高亮的
		highlightBuilder.preTags("<span style='background-color: #09fe09!important;color: #710606!important;'>");// 前缀
		highlightBuilder.postTags("</span>");// 后缀
		highlightBuilder.requireFieldMatch(true);// 是否多个字段高亮
		// 下面这两项,如果你要高亮如文字内容等有很多字的字段,必须配置,不然会导致高亮不全,文章内容缺失等
		highlightBuilder.fragmentSize(800000); // 最大高亮分片数
		highlightBuilder.numOfFragments(0); // 从第一个分片获取高亮片段

		sourceBuilder.highlighter(highlightBuilder);// 添加高亮
		// 全部匹配
		BoolQueryBuilder should = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("title", keyword))
				.should(QueryBuilders.matchQuery("description", keyword))
				.should(QueryBuilders.matchQuery("tags", keyword))
				.should(QueryBuilders.matchQuery("typeName", keyword));
		// 默认分数排序
		sourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));//
		// 分数一样 按照时间字段排序
		sourceBuilder.sort(new FieldSortBuilder("updateTime").order(SortOrder.DESC));
		sourceBuilder.query(should);
		sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
		// 执行搜索
		searchRequest.source(sourceBuilder);
		SearchResponse search;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			search = client.search(searchRequest, RequestOptions.DEFAULT);
			// 解析结果
			for (SearchHit hit : search.getHits()) {
				System.out.println(hit.getScore());
				Map<String, HighlightField> highlightFields = hit.getHighlightFields();
				HighlightField title = highlightFields.get("title");
				HighlightField description = highlightFields.get("description");
				HighlightField tags = highlightFields.get("tags");
				HighlightField typeName = highlightFields.get("typeName");
				Map<String, Object> asMap = hit.getSourceAsMap();// 原来的结果
				// 解析高亮的字段将原来的字段换成我们
				// 千万记得要记得判断是不是为空,不然你匹配的第一个结果没有高亮内容,那么就会报空指针异常,这个错误一开始真的搞了很久
				if (title != null) {
					Text[] fragments = title.fragments();
					String n_title = "";
					for (Text text : fragments) {
						n_title += text;
					}
					asMap.put("title", n_title);
				}
				if (description != null) {
					Text[] fragments = description.fragments();
					String n_title = "";
					for (Text text : fragments) {
						n_title += text;
					}
					asMap.put("description", n_title);
				}
				if (tags != null) {
					Text[] fragments = tags.fragments();
					String n_title = "";
					for (Text text : fragments) {
						n_title += text;
					}
					asMap.put("tags", n_title);
				}
				if (typeName != null) {
					Text[] fragments = typeName.fragments();
					String n_title = "";
					for (Text text : fragments) {
						n_title += text;
					}
					asMap.put("typeName", n_title);
				}
				list.add(asMap);
			}
		} catch (IOException e) {
			logger.info("com.myh.service.impl.EsServiceImpl.getListAll 出了异常" + new Date() + e.getMessage());
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void initEsData(String indexName) {
		List<ESBlog> listEsBlog = blogMapper.getListEsBlog();
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("30s");
		// 批处理 不写id 则随机id
		for (int i = 0; i < listEsBlog.size(); i++) {
			ESBlog esBlog = listEsBlog.get(i);
			String tagIds = esBlog.getTagIds();
			Integer typeId = esBlog.getTypeId();
			Type selectTypeOne = typeMapper.selectTypeOne(typeId);
			esBlog.setTypeName(selectTypeOne.getName());
			List<Tag> listByIdsTag = tagServiceImpl.listByIdsTag(tagIds);
			ArrayList<String> arrayList = new ArrayList<String>();
			for (Tag tag : listByIdsTag) {
				arrayList.add(tag.getName());
			}
			esBlog.setTags(arrayList);
			bulkRequest.add(new IndexRequest(indexName).id(esBlog.getId() + "").source(JSON.toJSONString(esBlog),
					XContentType.JSON));
		}
		BulkResponse bulk = null;
		try {
			bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			logger.info("初始化Es发送异常" + new Date());
			System.out.println("初始化Es发送异常");// 是否失败
			e.printStackTrace();
		}
		if (bulk.hasFailures()) {
			logger.info("初始化Es数据失败" + new Date());
			System.out.println("初始化Es数据失败");// 是否失败
		}
	}

	// 更新文档 根据博客id
	@Override
	public int updatedocumentById(Long id) {
		String indexName = "meblog";
		// 先创建索引 理论上不需要
		createIndex(indexName);
		UpdateRequest updateRequest = new UpdateRequest(indexName, id + "");
		updateRequest.timeout("10s"); // 设置时间
		ESBlog esBlog = getESBlogById(id);//根据博客id构建es博客对象
		// 开始更新文档
		int successfulCount = 0;
		updateRequest.doc(JSON.toJSONString(esBlog), XContentType.JSON); // 开始更新
		try {
			UpdateResponse update = client.update(updateRequest, RequestOptions.DEFAULT);
			successfulCount = update.getShardInfo().getSuccessful();// 获取更新条数
			return successfulCount;
		} catch (IOException e) {
			logger.info("更新文档 根据博客id update失败   com.myh.service.impl" + new Date() + "博客id" + id);
			e.printStackTrace();
		} // 执行更新
		return successfulCount;

	}

	// 删除一个文档
	@Override
	public int deldocumentById(Long id) {
		String indexName= "meblog";
		// 先创建索引 理论上不需要
		createIndex(indexName);
		DeleteRequest request = new DeleteRequest(indexName, id+"");
		request.timeout("10s");
		int successful=0;
		try {
			DeleteResponse delete = client.delete(request, RequestOptions.DEFAULT);
			successful = delete.getShardInfo().getSuccessful();
		} catch (IOException e) {
			logger.info("删除一个文档deldocumentById 出现异常 com.myh.service.impl" + new Date() + "博客id" + id);
			e.printStackTrace();
		}
		return successful;
	}
	//保存一个es博客对象
	@Override
	public int savedocumentById(Long id) {
		ESBlog esblog = getESBlogById(id);
		String indexName = "meblog";
		// 先创建索引 理论上不需要
		createIndex(indexName);
		// 创建请求
		IndexRequest request = new IndexRequest(indexName);
		request.id(esblog.getId() + "");
		request.timeout(TimeValue.timeValueSeconds(1));
		request.timeout("10s");
		// 将我们的数据放入请求 json
		System.out.println(JSON.toJSONString(esblog));
		request.source(JSON.toJSONString(esblog), XContentType.JSON);
		// 客户端发送请求
		int successfulcount = 0;
		try {
			IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
			successfulcount = indexResponse.getShardInfo().getSuccessful();
		} catch (IOException e) {
			logger.info("保存一个es博客对象失败savedocumentById  com.myh.service.impl" + new Date() + "博客id" + id);
			e.printStackTrace();
		}
		return successfulcount;
	}
		
	//根据博客id构建es博客对象
	public ESBlog getESBlogById(Long id) {
		ESBlog esBlog = blogMapper.updatedocumentById(id);
		// 注入标签名和类型名 ps：其实可以一个sql解决
		String tagIds = esBlog.getTagIds();
		Integer typeId = esBlog.getTypeId();
		Type selectTypeOne = typeMapper.selectTypeOne(typeId);
		esBlog.setTypeName(selectTypeOne.getName());
		System.out.println(selectTypeOne.getName());
		List<Tag> listByIdsTag = tagServiceImpl.listByIdsTag(tagIds);
		ArrayList<String> arrayList = new ArrayList<String>();
		for (Tag tag : listByIdsTag) {
			arrayList.add(tag.getName());
		}
		esBlog.setTags(arrayList);
		return esBlog;
	}
}
//查询请求生成
//SearchRequestBuilder requestBuilder = esClient.prepareSearch(indexname)//索引名字
//                .setTypes(indextype)      //索引类型
//                .setQuery(boolQuery)      //配置查询条件
//                .addSort(new ScoreSortBuilder())   //根据查询相关度进行排序
//                .addSort(timeSort)                 //再根据时间进行排序
//                .setTrackScores(true)             //避免分页之后相关性乱了
//                .highlighter(highlightBuilder)     //配置高亮
//                .setFrom(from)                 //设置分页
//                .setSize();