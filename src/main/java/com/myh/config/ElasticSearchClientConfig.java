package com.myh.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
**2020年7月13日--下午9:50:29
**@version:
**莫耀华:
**@Description:
*/
@Configuration
public class ElasticSearchClientConfig {
	
	
	@Bean("restHighLevelClient") //id 等于方法名  class 等于返回值
	public RestHighLevelClient restHighLevelClient() {
		// 默认为localhst:8080
		RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("127.0.0.1",9200,"http")));
		return client;
	}
}
