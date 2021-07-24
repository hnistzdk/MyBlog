package com.zdk.MyBlog.service.article;

import com.zdk.MyBlog.model.pojo.Article;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/22 17:06
 */
@Service
public interface ArticleService {
    /**
     * 获取所有文章信息
     * @return
     */
    List<Article> getAllArticle();

    /**
     * 根据id获取文章信息
     * @param id
     * @return
     */
    Article getArticleById(Integer id);

    /**
     * 添加文章
     * @param article
     * @return
     */
    Boolean addArticle(Article article);
}
