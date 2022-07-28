package com.zdk.blog.common.service;

import com.zdk.blog.common.dto.ArchiveDTO;
import com.zdk.blog.common.dto.MetaDTO;
import com.zdk.blog.common.dto.StatisticsDTO;
import com.zdk.blog.common.dto.cond.ArticleCond;
import com.zdk.blog.common.model.Article;
import com.zdk.blog.common.model.Comments;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/13 10:41
 */
public interface SiteService {
    /**
     * 获取评论列表
     * @param limit
     * @return
     */
    List<Comments> getComments(int limit);

    /**
     * 获取最新的文章
     * @param limit
     * @return
     */
    List<Article> getNewArticles(int limit);

    /**
     * 获取单条评论
     * @param coid
     * @return
     */
    Comments getComment(Integer coid);

    /**
     * 获取 后台统计数据
     * @return
     */
    StatisticsDTO getStatistics();

    /**
     * 获取归档列表 - 只是获取日期和数量
     * @param articleCond
     * @return
     */
    List<ArchiveDTO> getArchivesSimple(ArticleCond articleCond);

    /**
     * 获取归档列表
     * @param articleCond 查询条件（只包含开始时间和结束时间）
     * @return
     */
    List<ArchiveDTO> getArchives(ArticleCond articleCond);



    /**
     * 获取分类/标签列表
     * @param type
     * @param orderBy
     * @param limit
     * @return
     */
    List<MetaDTO> getMetas(String type, String orderBy, int limit);
}
