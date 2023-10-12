package com.jdk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.jdk.mapper")
public class SpringbootFurnApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootFurnApplication.class, args);
	}

}
