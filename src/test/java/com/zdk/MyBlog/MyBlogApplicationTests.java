package com.zdk.MyBlog;

import cn.hutool.json.JSONUtil;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.dto.cond.ArticleCond;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.service.relationships.RelationshipsService;
import com.zdk.MyBlog.utils.RedisUtil;
import com.zdk.MyBlog.utils.TaleUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@SpringBootTest
@Slf4j
class MyBlogApplicationTests extends BaseController {

	@Autowired
	RedisUtil redisUtil;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	ArticleService articleService;
	@Autowired
	RelationshipsService relationshipsService;

	@Test
	void contextLoads() {
		Object loginUser = redisUtil.hget(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request));
		User user = JSONUtil.toBean(JSONUtil.parseObj(loginUser), User.class);
		System.out.println("user = " + user);
	}

	@Test
	void test(){
		System.out.println("isOk(\"\") = " + isOk(null));
	}
	@Test
	void testCond(){
		ArticleCond articleCond = new ArticleCond().setTag("Java");
		List<Article> articleList = articleService.getArticleByCondition(articleCond);
		articleList.forEach(System.out::println);
	}

	@Test
	void testDelete(){
		relationshipsService.deleteByMetaId(1);
	}
}
