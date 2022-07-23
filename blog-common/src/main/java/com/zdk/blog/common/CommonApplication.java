package com.zdk.blog.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * @Description
 * @Author zdk
 * @Date 2022/7/23 15:05
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CommonApplication {
        public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
    }
}
