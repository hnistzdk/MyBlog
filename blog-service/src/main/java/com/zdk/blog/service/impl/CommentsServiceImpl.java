package com.zdk.blog.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.constant.ErrorConstant;
import com.zdk.blog.constant.RoleConst;
import com.zdk.blog.service.CommentsService;
import com.zdk.blog.mapper.CommentsMapper;
import com.zdk.blog.mapper.UserMapper;
import com.zdk.blog.dto.CommentsDTO;
import com.zdk.blog.model.Article;
import com.zdk.blog.model.Comments;
import com.zdk.blog.model.User;
import com.zdk.blog.service.ArticleService;
import com.zdk.blog.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService, ParaValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsServiceImpl.class);
    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleService articleService;

    @Cacheable(value = "comments",key = "'commentsByArticleId'+#articleId",condition = "#articleId!=null")
    @Override
    public List<Comments> getCommentsByArticleId(Integer articleId) {
        return lambdaQuery().eq(Comments::getArticleId, articleId)
                .eq(Comments::getStatus, "approved")
                .eq(Comments::getDeleted, false)
                .list();
    }

    @Cacheable(value = "comments",key = "'commentsByAuthorId'+#authorId",condition = "#authorId!=null")
    @Override
    public List<Comments> getCommentsByAuthorId(Integer authorId) {
        return lambdaQuery().eq(Comments::getAuthorId, authorId).eq(Comments::getDeleted, false).list();
    }

    @Cacheable(value = "comments",key = "'commentsByOwnerId'+#ownerId+#loginUser",condition = "#ownerId!=null")
    @Override
    public List<Comments> getCommentsByOwnerId(Integer ownerId, User loginUser) {
        return lambdaQuery().eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Comments::getOwnerId, ownerId)
                .eq(Comments::getDeleted, false)
                .list();
    }

    @Cacheable(value = "comments",key = "'commentsPage'+#pageNum+#pageSize+#loginUser")
    @Override
    public PageInfo<Comments> getCommentsPage(Integer pageNum, Integer pageSize,User loginUser) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comments> comments = lambdaQuery()
                .eq(!Objects.equals(loginUser.getRole(), RoleConst.ADMIN),Comments::getOwnerId, loginUser.getId())
                .eq(Comments::getDeleted, false)
                .orderByDesc(Comments::getCreateTime)
                .list();
        return new PageInfo<>(comments);
    }

    @CachePut(value = "comments",key = "'comments'+#commentsDto.id+#user")
    @Override
    public ApiResponse comment(CommentsDTO commentsDto, HttpServletRequest request, User user) {
        String ip = IpKit.getIpAddressByRequest(request);
        if (commentsDto == null){
            return ApiResponse.fail(ErrorConstant.Common.PARAM_IS_EMPTY);
        }
        String content = commentsDto.getContent();
        if (notOk(content)){
            return ApiResponse.fail("请输入完整评论再提交");
        }
        if (content.length() > 2000) {
            return ApiResponse.fail("请输入2000个字符以内的评论");
        }
        String email = commentsDto.getEmail();
        if (isOk(email) && !TaleUtils.isEmail(email)) {
            return ApiResponse.fail("请输入正确的邮箱格式");
        }
        String author = commentsDto.getAuthor();
        if (isOk(author) && author.length() > 20){
            return ApiResponse.fail("输入的名称过长");
        }
        String url = commentsDto.getUrl();
        if (isOk(url) && !PatternKit.isURL(url)) {
            return ApiResponse.fail("请输入正确的URL格式");
        }
        Integer articleId = commentsDto.getArticleId();
        if (notOk(articleId)){
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
        if (user != null){
            comments.setAuthorId(user.getId());
        }
        articleService.updateById(article);
        return ApiResponse.result(save(comments));
    }
}
