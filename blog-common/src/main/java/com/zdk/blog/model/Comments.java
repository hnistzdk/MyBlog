package com.zdk.blog.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author zdk
 * @since 2021-08-12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("blog_comments")
public class Comments implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联文章id
     */
    private Integer articleId;

    /**
     * 评论时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    /**
     * 评论者
     */
    private String author;

    /**
     * 评论者id
     */
    private Integer authorId;

    /**
     * 评论所属用户id
     */
    private Integer ownerId;

    /**
     * 评论者邮箱
     */
    private String email;

    /**
     * 评论者网址
     */
    private String url;

    /**
     * 评论者ip地址
     */
    private String ip;

    /**
     * 评论者客户端
     */
    private String agent;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论类型
     */
    private String type;

    /**
     * 评论状态
     */
    private String status;

    /**
     * 父级评论
     */
    private Integer parent;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;

}
