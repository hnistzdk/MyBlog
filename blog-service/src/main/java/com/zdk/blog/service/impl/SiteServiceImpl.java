package com.zdk.blog.service.impl;

import com.zdk.blog.service.SiteService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.ArticleMapper;
import com.zdk.blog.mapper.AttachMapper;
import com.zdk.blog.mapper.CommentsMapper;
import com.zdk.blog.mapper.MetasMapper;
import com.zdk.blog.dto.ArchiveDTO;
import com.zdk.blog.dto.MetaDTO;
import com.zdk.blog.dto.StatisticsDTO;
import com.zdk.blog.dto.cond.ArticleCond;
import com.zdk.blog.model.Article;
import com.zdk.blog.model.Comments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @date 2021/8/13 10:41
 */
@Service
public class SiteServiceImpl implements SiteService, ParaValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(SiteServiceImpl.class);

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private MetasMapper metasMapper;

    @Autowired
    private AttachMapper attachMapper;

    @Override
    public List<Comments> getComments(int limit) {
        return null;
    }

    @Override
    public List<Article> getNewArticles(int limit) {
        return null;
    }

    @Override
    public Comments getComment(Integer coid) {
        return null;
    }

    @Override
    public StatisticsDTO getStatistics() {
        return null;
    }

    @Override
    public List<ArchiveDTO> getArchivesSimple(ArticleCond articleCond) {
        return null;
    }

    @Override
    public List<ArchiveDTO> getArchives(ArticleCond articleCond) {
        return null;
    }

    @Override
    public List<MetaDTO> getMetas(String type, String orderBy, int limit) {
        return null;
    }
}
