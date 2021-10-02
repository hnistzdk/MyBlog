package com.zdk.MyBlog.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.model.dto.cond.ArticleCond;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.User;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/22 17:06
 */
public interface ArticleService extends IService<Article> {
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
     * 根据用户id获取文章信息
     * @param loginUser
     * @return
     */
    List<Article> getArticleByAuthorId(User loginUser);

    /**
     * 添加文章
     * @param article
     * @return
     */
    Boolean addArticle(Article article);

    /**
     * 通过id删除文章
     * @param id
     * @return
     */
    Boolean deleteArticleById(Integer id);

    /**
     * 获取文章分页
     * @param pageNum
     * @param pageSize
     * @param loginUser
     * @return
     */
    PageInfo<Article> getArticlePage(Integer pageNum,Integer pageSize,User loginUser);

    /**
     * 根据关键字获取文章分页
     * @param pageNum
     * @param pageSize
     * @param keywords
     * @return
     */
    PageInfo<Article> getArticlePageByKeywords(Integer pageNum,Integer pageSize,String keywords);

    /**
     * 将分类为oldCategory的文章全部替换为分类为newCategory
     * @param oldCategory
     * @param newCategory
     */
    void updateByCondition(String oldCategory,String newCategory);

    /**
     * 根据查询条件获取文章信息
     * @param articleCond
     * @return
     */
    List<Article> getArticleByCondition(ArticleCond articleCond);

    /**
     * 编辑文章后保存
     * @param article
     * @return
     */
    Boolean modifyArticle(Article article);

    /**
     * 获取最新的几篇文章
     * @return
     */
    List<Article> getLatestArticle();

    /**
     * 获取点击最高文章
     * @return
     */
    List<Article> getClickMostArticle();
}
