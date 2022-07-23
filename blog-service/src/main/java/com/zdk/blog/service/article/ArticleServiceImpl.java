package com.zdk.blog.service.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.common.constant.RoleConst;
import com.zdk.blog.common.constant.Types;
import com.zdk.blog.common.dto.cond.ArticleCond;
import com.zdk.blog.common.model.Article;
import com.zdk.blog.common.model.User;
import com.zdk.blog.common.utils.ParaValidatorUtil;
import com.zdk.blog.mapper.ArticleMapper;
import com.zdk.blog.service.metas.MetasService;
import com.zdk.blog.service.relationships.RelationshipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zdk
 * @date 2021/7/22 17:07
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RelationshipsService relationshipsService;
    @Autowired
    private MetasService metasService;

    @Autowired
    private ParaValidatorUtil paraValidatorUtil;

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
        if (paraValidatorUtil.notOk(id)){
            return null;
        }
        System.out.println("执行了查询");
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
        if (paraValidatorUtil.notOk(loginUser.getId())){
            return null;
        }
        return lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Article::getAuthorId, loginUser.getId()).list();
    }

    @CachePut(cacheNames="article",key="#article.id")
    @Override
    public Boolean addArticle(Article article) {
        return saveOrUpdate(article);
    }

    @Caching(evict={@CacheEvict(value = "article", key="'article'+#id",condition="#id!=null")
            , @CacheEvict(value = "article", key="'articles'")})
    @Override
    public Boolean deleteArticleById(Integer id) {
        if (paraValidatorUtil.notOk(id)){
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
        List<Article> articles = lambdaQuery().like(paraValidatorUtil.isOk(keywords),Article::getTitle,keywords).
                or().like(paraValidatorUtil.isOk(keywords),Article::getContent,keywords)
                .like(paraValidatorUtil.isOk(tag),Article::getTags,tag)
                .orderByDesc(Article::getUpdateTime).list();
        return new PageInfo<>(articles);
    }

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
        return lambdaQuery().like(paraValidatorUtil.isOk(articleCond.getTag()),Article::getTags, articleCond.getTag())
                .like(paraValidatorUtil.isOk(articleCond.getCategory()),Article::getCategories, articleCond.getCategory())
                .eq(paraValidatorUtil.isOk(articleCond.getStatus()),Article::getStatus, articleCond.getStatus())
                .eq(paraValidatorUtil.isOk(articleCond.getTitle()),Article::getTitle, articleCond.getTitle())
                .eq(paraValidatorUtil.isOk(articleCond.getContent()),Article::getContent, articleCond.getContent())
                .eq(paraValidatorUtil.isOk(articleCond.getType()),Article::getType, articleCond.getType())
                .between(paraValidatorUtil.isOk(articleCond.getStartTime())&& paraValidatorUtil.isOk(articleCond.getEndTime()),Article::getUpdateTime, articleCond.getStartTime(),articleCond.getEndTime())
                .list();
    }

    @CachePut(cacheNames="article",key="#article.id")
    @Override
    public Boolean modifyArticle(Article article) {
        int update = articleMapper.updateById(article);
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
