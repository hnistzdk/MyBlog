package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.constant.RoleConst;
import com.zdk.blog.constant.Types;
import com.zdk.blog.dto.cond.ArticleCond;
import com.zdk.blog.model.Article;
import com.zdk.blog.model.User;
import com.zdk.blog.request.article.ArticleCreateRequest;
import com.zdk.blog.service.ArticleService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.ArticleMapper;
import com.zdk.blog.service.MetasService;
import com.zdk.blog.service.RelationshipsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zdk
 * @date 2021/7/22 17:07
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService,ParaValidator {

    @Autowired
    private RelationshipsService relationshipsService;
    @Autowired
    private MetasService metasService;

    @Cacheable(value = "article",key = "'articles'")
    @Override
    public List<Article> getAllArticle() {
        return list();
    }

    /**
     * unless 参数就是不执行Cacheable的条件
     * @param id
     * @return
     */
    @Cacheable(value = "article",key = "'article'+#id",unless = "#id==null")
    @Override
    public Article getArticleById(Integer id) {
        if (notOk(id)){
            return null;
        }
        return getById(id);
    }

    /**
     * condition 参数就是执行Cacheable的条件
     * @param loginUser
     * @return
     */
    @Cacheable(value = "article",key = "'article'+#loginUser.username",condition = "#loginUser!=null")
    @Override
    public List<Article> getArticleByAuthorId(User loginUser) {
        if (notOk(loginUser.getId())){
            return null;
        }
        return lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Article::getAuthorId, loginUser.getId()).list();
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @CachePut(cacheNames="article",key="#article.id")
    @Override
    public void add(ArticleCreateRequest createRequest,User loginUser){
        Article article = new Article();
        BeanUtils.copyProperties(createRequest, article);
        save(article);
        article.setAuthorId(loginUser.getId()).setAuthorName(loginUser.getNickname());
        //去除文章储存时的多余逗号
        article.setContent(article.getContent().replaceAll("^,", ""));
        article.setCategories(article.getCategories().replaceAll("^,", ""));
        metasService.addMetas(article.getId(),article.getCategories(), Types.CATEGORY.getType());
        metasService.addMetas(article.getId(),article.getTags(), Types.TAG.getType());
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Caching(evict={@CacheEvict(value = "article", key="'article'+#id",condition="#id!=null")
            , @CacheEvict(value = "article", key="'articles'")})
    @Override
    public Boolean deleteArticleById(Integer id) {
        if (notOk(id)){
            return null;
        }
        return removeById(id);
    }

    @Cacheable(value = "article",key = "'articlePage'+#pageNum+#pageSize+#loginUser.username")
    @Override
    public PageInfo<Article> getArticlePage(Integer pageNum, Integer pageSize,User loginUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN), Article::getAuthorId, loginUser.getId()).orderByDesc(Article::getUpdateTime).list();
        return new PageInfo<>(articles);
    }

    @Cacheable(value = "article",key = "'articlePage'+#pageNum+#pageSize+#keywords+#tag")
    @Override
    public PageInfo<Article> getArticlePageByKeywords(Integer pageNum, Integer pageSize,String keywords,String tag) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = lambdaQuery().like(isOk(keywords),Article::getTitle,keywords).
                or().like(isOk(keywords),Article::getContent,keywords)
                .like(isOk(tag),Article::getTags,tag)
                .orderByDesc(Article::getUpdateTime).list();
        return new PageInfo<>(articles);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @Override
    public void updateByCondition(String oldCategory, String newCategory) {
        List<Article> list = lambdaQuery().eq(Article::getCategories, oldCategory).list();
        list.forEach(article -> {
                    article.setCategories(article.getCategories().replaceAll(oldCategory, newCategory));
                    updateById(article);
                });
    }

    @Cacheable(value = "article",key = "'articles'+#articleCond")
    @Override
    public List<Article> getArticleByCondition(ArticleCond articleCond) {
        return lambdaQuery().like(isOk(articleCond.getTag()),Article::getTags, articleCond.getTag())
                .like(isOk(articleCond.getCategory()),Article::getCategories, articleCond.getCategory())
                .eq(isOk(articleCond.getStatus()),Article::getStatus, articleCond.getStatus())
                .eq(isOk(articleCond.getTitle()),Article::getTitle, articleCond.getTitle())
                .eq(isOk(articleCond.getContent()),Article::getContent, articleCond.getContent())
                .eq(isOk(articleCond.getType()),Article::getType, articleCond.getType())
                .between(isOk(articleCond.getStartTime())&& isOk(articleCond.getEndTime()),Article::getUpdateTime, articleCond.getStartTime(),articleCond.getEndTime())
                .list();
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Throwable.class)
    @CachePut(cacheNames="article",key="#article.id")
    @Override
    public Boolean modifyArticle(Article article) {
        int update = baseMapper.updateById(article);
        relationshipsService.deleteByArticleId(article.getId());
        metasService.addMetas(article.getId(), article.getCategories(), Types.CATEGORY.getType());
        metasService.addMetas(article.getId(), article.getTags(), Types.TAG.getType());
        return update>0;
    }

    @Cacheable(value = "article",key = "'articlesLatest'")
    @Override
    public List<Article> getLatestArticle() {
        return lambdaQuery().orderByDesc(Article::getPublishTime).list();
    }

    @Cacheable(value = "article",key = "'articlesClickMost'")
    @Override
    public List<Article> getClickMostArticle() {
        return lambdaQuery().orderByDesc(Article::getReadCount).list();
    }
}
