package com.myh.config;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.myh.utils.RedisUtils;

@Configuration //配置类
public class RedisConfig {  
	@Bean
	@SuppressWarnings("all")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)
			throws UnknownHostException {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		//使用json序列化
		Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om=new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);		
		serializer.setObjectMapper(om);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
		//key 采用string的序列化方法
		template.setKeySerializer(stringRedisSerializer);
		//hash的key采用string的序列化方法
		template.setHashKeySerializer(stringRedisSerializer);
		 //value 的序列化使用jackJson的
		template.setValueSerializer(serializer);
		//hash的value采用string的序列化方法
		template.setHashValueSerializer(serializer);
		template.afterPropertiesSet();
		return template;
	}
	
	//
	@Bean(value = "redisUtils")
	public RedisUtils redisUtils() {
		return new RedisUtils();
	}
}
