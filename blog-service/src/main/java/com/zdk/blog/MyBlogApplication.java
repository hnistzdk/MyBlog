package com.zdk.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zdk
 * @date 2021/7/6
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableTransactionManagement
@MapperScan("com.zdk.blog.mapper")
public class MyBlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyBlogApplication.class, args);
	}
}
