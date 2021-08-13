package com.zdk.MyBlog.service.site;

import com.zdk.MyBlog.mapper.ArticleMapper;
import com.zdk.MyBlog.mapper.AttachMapper;
import com.zdk.MyBlog.mapper.CommentsMapper;
import com.zdk.MyBlog.mapper.MetasMapper;
import com.zdk.MyBlog.model.dto.ArchiveDto;
import com.zdk.MyBlog.model.dto.MetaDto;
import com.zdk.MyBlog.model.dto.StatisticsDto;
import com.zdk.MyBlog.model.dto.cond.ArticleCond;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.Comments;
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
public class SiteServiceImpl implements SiteService{
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
    public StatisticsDto getStatistics() {
        return null;
    }

    @Override
    public List<ArchiveDto> getArchivesSimple(ArticleCond articleCond) {
        return null;
    }

    @Override
    public List<ArchiveDto> getArchives(ArticleCond articleCond) {
        return null;
    }

    @Override
    public List<MetaDto> getMetas(String type, String orderBy, int limit) {
        return null;
    }
}
