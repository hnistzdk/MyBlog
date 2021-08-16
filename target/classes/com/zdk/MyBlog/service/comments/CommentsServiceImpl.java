package com.zdk.MyBlog.service.comments;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.RoleConst;
import com.zdk.MyBlog.mapper.CommentsMapper;
import com.zdk.MyBlog.mapper.UserMapper;
import com.zdk.MyBlog.model.pojo.Comments;
import com.zdk.MyBlog.model.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

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
    @Autowired
    private UserMapper userMapper;

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
        return lambdaQuery().eq(Comments::getArticleId, articleId).list();
    }

    @Override
    public List<Comments> getCommentsByAuthorId(Integer authorId) {
        return lambdaQuery().eq(Comments::getAuthorId, authorId).list();
    }

    @Override
    public List<Comments> getCommentsByOwnerId(Integer ownerId, User loginUser) {
        return lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Comments::getOwnerId, ownerId).list();
    }

    @Override
    public PageInfo<Comments> getCommentsPage(Integer pageNum, Integer pageSize,User loginUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comments> comments = lambdaQuery().eq(Comments::getOwnerId, loginUser.getId()).list();
        return new PageInfo<>(comments);
    }
}
