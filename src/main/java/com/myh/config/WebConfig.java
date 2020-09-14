package com.myh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
**2020年4月25日--下午10:47:58
**@version:
**莫耀华:
**@Description:
*/
//接管mvc的配置
@Configuration
public class WebConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		WebMvcConfigurer.super.addViewControllers(registry);
//		registry.addViewController("/").setViewName("index");
//		registry.addViewController("/index.html").setViewName("index");
//		registry.addViewController("/main.html").setViewName("dashboard");
		registry.addViewController("/").setViewName("redirect:index.html");
		registry.addViewController("/index").setViewName("index");
		registry.addViewController("/admin").setViewName("admin/login");
		registry.addViewController("/admin/index").setViewName("admin/index");
	}
	
	/**
	 * 解决跨域问题
	 */
	@Configuration
	public class CorsConfig implements WebMvcConfigurer {
	    @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/**")
	                .allowedOrigins("*")
	                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
	                .allowCredentials(true)
	                .maxAge(3600)
	                .allowedHeaders("*");
	    }
	}

	//注册拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoginInterceptor())
		.addPathPatterns("/admin/**")
		.excludePathPatterns("/admin")
		.excludePathPatterns("/admin/login")
		.excludePathPatterns("/admin/login.html")
		.excludePathPatterns("/admin/")
		.excludePathPatterns("/admin/logout");
	}
	

	
}
