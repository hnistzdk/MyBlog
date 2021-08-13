package com.zdk.MyBlog.service.comments;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.MyBlog.model.pojo.Comments;

import java.io.Serializable;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
public interface CommentsService extends IService<Comments> {
    /**
     * 根据传入条件获取评论
     * @param queryWrapper
     * @return
     */
    @Override
    default Comments getOne(Wrapper<Comments> queryWrapper) {
        return IService.super.getOne(queryWrapper);
    }

    /**
     * 根据评论id获取评论
     * @param id
     * @return
     */
    @Override
    default Comments getById(Serializable id) {
        return IService.super.getById(id);
    }

    /**
     * 获取所有评论list
     * @return
     */
    @Override
    default List<Comments> list() {
        return IService.super.list();
    }

    /**
     * 根据文章id获取评论
     * @param articleId
     * @return
     */
    List<Comments> getCommentsByArticleId(Integer articleId);

    /**
     * 根据评论者id获取评论
     * @param authorId
     * @return
     */
    List<Comments> getCommentsByAuthorId(Integer authorId);

    /**
     * 根据被评论者id获取评论
     * @param ownerId
     * @return
     */
    List<Comments> getCommentsByOwnerIdId(Integer ownerId);


}
