package com.zdk.MyBlog.service.comments;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.dto.CommentsDTO;
import com.zdk.MyBlog.model.Comments;
import com.zdk.MyBlog.model.User;
import com.zdk.MyBlog.utils.ApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
public interface CommentsService extends IService<Comments> {
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
     * @param loginUser
     * @return
     */
    List<Comments> getCommentsByOwnerId(Integer ownerId, User loginUser);


    /**
     * 获取评论分页
     * @param pageNum
     * @param pageSize
     * @param loginUser
     * @return
     */
    PageInfo<Comments> getCommentsPage(Integer pageNum,Integer pageSize,User loginUser);

    /**
     * 添加评论
     * @param comments
     * @param request
     * @param user
     * @return
     */
    ApiResponse comment(CommentsDTO comments, HttpServletRequest request, User user);
}
