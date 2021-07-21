package com.zdk.MyBlog;

import com.zdk.MyBlog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class MyBlogApplicationTests {

	@Autowired
	RedisUtil redisUtil;

	@Test
	void contextLoads() {
		redisUtil.del("xx");
	}
}
