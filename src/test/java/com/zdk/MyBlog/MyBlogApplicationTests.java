package com.zdk.MyBlog;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class MyBlogApplicationTests {

	@Autowired
	RedisTemplate redisTemplate;

	private static final Logger logger = LoggerFactory.getLogger(MyBlogApplicationTests.class);

	@Test
	void contextLoads() {
		RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
		connection.flushDb();
		redisTemplate.opsForValue().set("k1", "zdk");
		System.out.println("redisTemplate.opsForValue().get(\"k1\") = " + redisTemplate.opsForValue().get("k1"));
		logger.info("测试日志");
	}

}
