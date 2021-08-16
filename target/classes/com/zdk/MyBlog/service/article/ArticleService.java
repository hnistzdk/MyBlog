package com.zdk.MyBlog.service.article;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
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
}
