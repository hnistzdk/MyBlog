package com.zdk.MyBlog.service.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.mapper.ArticleMapper;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.utils.ParaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/22 17:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService{

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public List<Article> getAllArticle() {
        return articleMapper.selectList(null);
    }

    @Override
    public Article getArticleById(Integer id) {
        if (ParaValidator.notOk(id)){
            return null;
        }
        return articleMapper.selectById(id);
    }

    @Override
    public Article getArticleByUserId(String username) {
        if (ParaValidator.notOk(username)){
            return null;
        }
        return getOne(query().eq("username", username));
    }

    @Override
    public Boolean addArticle(Article article) {
        return articleMapper.insert(article)>0;
    }

    @Override
    public Boolean deleteArticleById(Integer id) {
        if (ParaValidator.notOk(id)){
            return null;
        }
        return removeById(id);
    }

    @Override
    public PageInfo<Article> getArticlePage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectList(null);
        return new PageInfo<>(articles);
    }
}
