package com.zdk.MyBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/**
 * @author zdk
 * @date 2021/7/6
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MyBlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(MyBlogApplication.class, args);
	}

}
