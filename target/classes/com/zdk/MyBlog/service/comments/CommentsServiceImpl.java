package com.zdk.MyBlog.service.comments;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.CommentsMapper;
import com.zdk.MyBlog.model.pojo.Comments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);
    @Autowired
    private CommentsMapper commentsMapper;

    @Override
    public Comments getOne(Wrapper<Comments> queryWrapper) {
        return CommentsService.super.getOne(queryWrapper);
    }

    @Override
    public Comments getById(Serializable id) {
        return CommentsService.super.getById(id);
    }

    @Override
    public List<Comments> list() {
        return CommentsService.super.list();
    }

    @Override
    public List<Comments> getCommentsByArticleId(Integer articleId) {
        return list(Wrappers.<Comments>lambdaQuery().eq(Comments::getArticleId, articleId));
    }

    @Override
    public List<Comments> getCommentsByAuthorId(Integer authorId) {
        return list(Wrappers.<Comments>lambdaQuery().eq(Comments::getAuthorId, authorId));
    }

    @Override
    public List<Comments> getCommentsByOwnerIdId(Integer ownerId) {
        return list(Wrappers.<Comments>lambdaQuery().eq(Comments::getOwnerId, ownerId));
    }
}
