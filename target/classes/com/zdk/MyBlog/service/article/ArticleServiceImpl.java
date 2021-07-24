package com.zdk.MyBlog.service.article;

import com.zdk.MyBlog.mapper.ArticleMapper;
import com.zdk.MyBlog.model.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/22 17:07
 */
@Service
public class ArticleServiceImpl implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAllArticle() {
        return articleMapper.getAllArticle();
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public Boolean addArticle(Article article) {
        return articleMapper.addArticle(article)>0;
    }
}
