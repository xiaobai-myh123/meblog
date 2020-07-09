package com.myh.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
**2020年6月30日--下午5:03:34
**@version:
**莫耀华:
**@Description: 接口文档 Api
*/
@Configuration
@EnableSwagger2
public class Swagger2Config {
	public Docket docket(Environment environment) {
		//获取项目环境
		//设置要显示的swagger环境
//		Profiles profiles = Profiles.of("dev","pro");
		//通过environment.acceptsProfiles(profiles) 是否在特定的环境中
//		boolean flag = environment.acceptsProfiles(profiles);
		Docket docket = new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.groupName("java")//分组
				.enable(true)//是否启动
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.myh.controller"))
				.build();
				
		return docket;
	}
	//配置swagger信息=apiInfo
	private ApiInfo apiInfo() {
		Contact DEFAULT_CONTACT = new Contact("小白","http://127.0.0.1","123456789@qq.com");
		return new ApiInfo(
				"博客Api测文档",
				"个人博客的API接口文档", 
				"version 1.0", 
				"termsOfServiceUrl urn:tos",
				DEFAULT_CONTACT, 
				"Apache 2.0", 
				"http://www.apache.org/licenses/LICENSE-2.0", 
				new ArrayList<VendorExtension>());
	}
}
