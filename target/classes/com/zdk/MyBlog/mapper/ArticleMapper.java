package com.zdk.MyBlog.mapper;

import com.zdk.MyBlog.model.pojo.Article;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/22 17:07
 */
@Mapper
public interface ArticleMapper {

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
     * 根据username获取文章信息
     * @param username
     * @return
     */
    Article getArticleByUserId(String username);


    /**
     * 添加文章
     * @param article
     * @return
     */
    int addArticle(Article article);
}
