package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(value = "com.example.demo.mapper")//或者在mapper(Dao层)添加 @Mapper public interface UserMapper
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}