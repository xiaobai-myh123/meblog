package com.myh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.myh.mapper")
public class MeblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeblogApplication.class, args);
	}

}
  