package com.zdk.MyBlog.service.comments;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.ErrorConstant;
import com.zdk.MyBlog.constant.RoleConst;
import com.zdk.MyBlog.mapper.CommentsMapper;
import com.zdk.MyBlog.mapper.UserMapper;
import com.zdk.MyBlog.model.dto.CommentsDto;
import com.zdk.MyBlog.model.pojo.Article;
import com.zdk.MyBlog.model.pojo.Comments;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.article.ArticleService;
import com.zdk.MyBlog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private ParaValidatorUtil paraValidatorUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ArticleService articleService;

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
        List<Comments> comments = lambdaQuery()
                .eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Comments::getOwnerId, loginUser.getId())
                .orderByDesc(Comments::getCreateTime)
                .list();
        return new PageInfo<>(comments);
    }

    @Override
    public ApiResponse comment(CommentsDto commentsDto, HttpServletRequest request) {
        String ip = IpKit.getIpAddressByRequest(request);
        if (commentsDto == null){
            return ApiResponse.fail(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        String content = commentsDto.getContent();
        if (paraValidatorUtil.notOk(content)){
            return ApiResponse.fail("请输入完整评论再提交");
        }
        if (content.length() > 2000) {
            return ApiResponse.fail("请输入200个字符以内的评论");
        }
        String email = commentsDto.getEmail();
        if (paraValidatorUtil.isOk(email) && !TaleUtils.isEmail(email)) {
            return ApiResponse.fail("请输入正确的邮箱格式");
        }
        String author = commentsDto.getAuthor();
        if (paraValidatorUtil.isOk(author) && author.length() > 20){
            return ApiResponse.fail("输入的名称过长");
        }
        String url = commentsDto.getUrl();
        if (paraValidatorUtil.isOk(url) && !PatternKit.isURL(url)) {
            return ApiResponse.fail("请输入正确的URL格式");
        }
        Integer articleId = commentsDto.getArticleId();
        if (paraValidatorUtil.notOk(articleId)){
            return ApiResponse.fail("评论文章不能为空");
        }
        //去除HTML脚本 防止XSS注入攻击
        author = TaleUtils.cleanXSS(author);
        content = TaleUtils.cleanXSS(content);
        Article article = articleService.getArticleById(articleId);
        article.setCommentCount(article.getCommentCount()+1);
        Comments comments = new Comments();
        comments.setContent(content)
                .setAuthor(author)
                .setCreateTime(DateUtil.now())
                .setArticleId(article.getId())
                .setEmail(email)
                .setOwnerId(article.getAuthorId())
                .setIp(ip)
                .setAgent(request.getHeader("agent"));
        articleService.updateById(article);
        return ApiResponse.result(save(comments));
    }
}
