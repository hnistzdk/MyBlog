package com.zdk.MyBlog.service.article;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.RoleConst;
import com.zdk.MyBlog.constant.Types;
import com.zdk.MyBlog.mapper.ArticleMapper;
import com.zdk.MyBlog.model.dto.cond.ArticleCond;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.metas.MetasService;
import com.zdk.MyBlog.service.relationships.RelationshipsService;
import com.zdk.MyBlog.utils.ParaValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    @Override
    public List<Article> getAllArticle() {
        return articleMapper.selectList(null);
    }

    @Override
    public Article getArticleById(Integer id) {
        if (paraValidatorUtil.notOk(id)){
            return null;
        }
        return articleMapper.selectById(id);
    }

    @Override
    public List<Article> getArticleByAuthorId(User loginUser) {
        if (paraValidatorUtil.notOk(loginUser.getId())){
            return null;
        }
        return lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Article::getAuthorId, loginUser.getId()).list();
    }

    @Override
    public Boolean addArticle(Article article) {
        return articleMapper.insert(article)>0;
    }

    @Override
    public Boolean deleteArticleById(Integer id) {
        if (paraValidatorUtil.notOk(id)){
            return null;
        }
        return removeById(id);
    }

    @Override
    public PageInfo<Article> getArticlePage(Integer pageNum, Integer pageSize,User loginUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN), Article::getAuthorId, loginUser.getId()).orderByDesc(Article::getUpdateTime).list();
        return new PageInfo<>(articles);
    }

    @Override
    public PageInfo<Article> getArticlePageByKeywords(Integer pageNum, Integer pageSize,String keywords) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = lambdaQuery().like(paraValidatorUtil.isOk(keywords),Article::getTitle,keywords).
                or().like(paraValidatorUtil.isOk(keywords),Article::getContent,keywords)
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

    @Override
    public Boolean modifyArticle(Article article) {
        int update = articleMapper.updateById(article);
        relationshipsService.deleteByArticleId(article.getId());
        metasService.addMetas(article.getId(), article.getCategories(), Types.CATEGORY.getType());
        metasService.addMetas(article.getId(), article.getTags(), Types.TAG.getType());
        return update>0;
    }

    @Override
    public List<Article> getLatestArticle() {
        return lambdaQuery().orderByDesc(Article::getPublishTime).list();
    }

    @Override
    public List<Article> getClickMostArticle() {
        return lambdaQuery().orderByDesc(Article::getReadCount).list();
    }
}
