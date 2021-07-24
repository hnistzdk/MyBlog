package com.zdk.MyBlog;

import cn.hutool.json.JSONUtil;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.pojo.User;
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
		Object loginUser = redisUtil.hget(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);
		User user = JSONUtil.toBean(JSONUtil.parseObj(loginUser), User.class);
		System.out.println("user = " + user);
	}

	@Test
	void test(){
	}
}
