package com.zdk.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zdk
 * @date 2021/11/20 18:11
 * 添加评论Dto
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentsDTO {
    /**
     * 评论id
     */
    private Integer id;
    /**
     * 文章id
     */
    private Integer articleId;
    /**
     * 评论者
     */
    private String author;
    /**
     * 评论者邮箱
     */
    private String email;
    /**
     * 评论者的网站链接
     */
    private String url;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 访问token(暂时不需要)
     */
    private String csrfToken;
}
