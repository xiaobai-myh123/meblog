package com.myh;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.fastjson.JSON;
import com.myh.mapper.BlogMapper;
import com.myh.mapper.TypeMapper;
import com.myh.pojo.ESBlog;
import com.myh.pojo.Tag;
import com.myh.pojo.Type;
import com.myh.service.impl.TagServiceImpl;

@SpringBootTest
class MeblogApplicationTests {
	
	@Autowired
	private RestHighLevelClient client;
	
	@Autowired
	private BlogMapper blogMapper;
	@Autowired
	private TypeMapper typeMapper;
	@Autowired
	private TagServiceImpl tagServiceImpl;
	
	@Test
	void contextLoads() {
		System.out.println(client);
	}

	// 创建索引
	@Test
	void createIndex() throws Exception {
		String esIndex = "meblog";
		// 测试索引是否存在
		GetIndexRequest getIndexRequest = new GetIndexRequest(esIndex);
		boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
		System.out.println(exists);
		if (!exists) {
			// 不存在
			// 1.创建索引请求
			CreateIndexRequest createIndexRequest = new CreateIndexRequest(esIndex);
			// 2.客户端执行请求 返回一个IndicesClient 请求后获取响应
			CreateIndexResponse create = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
			System.out.println(create.isAcknowledged());
			System.out.println(create.isShardsAcknowledged());
		}
	}
	
	//查询插入数据
	@Test
	void InsertData() throws Exception {
		List<ESBlog> listEsBlog = blogMapper.getListEsBlog();
		BulkRequest bulkRequest = new BulkRequest();
		bulkRequest.timeout("30s");
		//批处理 不写id  则随机id
		for (int i = 0; i < listEsBlog.size(); i++) {
			ESBlog esBlog=listEsBlog.get(i);
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
			bulkRequest.add(new IndexRequest("meblog").id(esBlog.getId()+"").source(JSON.toJSONString(esBlog),
					XContentType.JSON));
		}
		BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
		System.out.println(bulk);
		System.out.println(bulk.hasFailures());//是否失败
	}
	//多条件查询
	//多条件
		@Test 
		void testSearch3() throws Exception {
			int pageNo=0;
			int pageSize=10;
			String keyword="入了上门";
			//条件搜索
			SearchRequest searchRequest = new SearchRequest("meblog");
			SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

			//分页
			sourceBuilder.from(pageNo);
			sourceBuilder.size(pageSize);
			
			//高亮
			HighlightBuilder highlightBuilder = new HighlightBuilder();
			highlightBuilder.field("title");//高亮的
			highlightBuilder.preTags("<span background-color: #09fe09!important;color: #710606!important;");//前缀
			highlightBuilder.postTags("</span>");//后缀
			highlightBuilder.requireFieldMatch(true);//是否多个字段高亮
			sourceBuilder.highlighter(highlightBuilder);
			
			//全部匹配
			BoolQueryBuilder should = QueryBuilders.boolQuery()
					.should(QueryBuilders.matchQuery("title", keyword))
					.should(QueryBuilders.matchQuery("description", keyword))
					.should(QueryBuilders.matchQuery("tags", keyword));
			sourceBuilder.query(should);
			sourceBuilder.timeout(new TimeValue(60,TimeUnit.SECONDS));
			//执行搜索
			searchRequest.source(sourceBuilder);
			SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
			//解析结果
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for (SearchHit hit : search.getHits()) {
				Map<String, HighlightField> highlightFields = hit.getHighlightFields();
				HighlightField title = highlightFields.get("title");
				HighlightField description = highlightFields.get("description");
				HighlightField tags = highlightFields.get("tags");
				Map<String, Object> asMap = hit.getSourceAsMap();//原来的结果
				
				//解析高亮的字段将原来的字段换成我们
				if(title!=null) {
					Text[] fragments = title.fragments();
					String n_title="";
					for (Text text : fragments) {
						n_title+=text;
					}
					asMap.put("title", n_title);
				}
				if(description!=null) {
					Text[] fragments = description.fragments();
					String n_title="";
					for (Text text : fragments) {
						n_title+=text;
					}
					asMap.put("title", n_title);
				}
				if(tags!=null) {
					Text[] fragments = tags.fragments();
					String n_title="";
					for (Text text : fragments) {
						n_title+=text;
					}
					asMap.put("title", n_title);
				}
				list.add(asMap);
			}
			System.out.println(list.size());
//			return list;
		}
	
}
