package com.zdk.blog.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author zdk
 * @date 2021/7/22 16:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("blog_article")
public class Article implements Serializable {
    /**
     * 文章主键编号
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 作者id
     */
    private Integer authorId;
    /**
     * 作者名
     */
    private String authorName;
    /**
     * 标题内容
     */
    private String title;
    /**
     * 标题图片
     */
    private String titlePicture;
    /**
     * 内容缩略名
     */
    private String slug;
    /**
     * 简介
     */
    private String introduction;
    /**
     * 内容主体
     */
    private String content;
    /**
     * 文章纯文本格式(渲染编辑页面时使用)
     */
    private String text;
    /**
     * 文章发布时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String publishTime;
    /**
     * 文章更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    /**
     * 阅读量
     */
    private Integer readCount;
    /**
     * 内容所属评论数
     */
    private Integer commentCount;
    @TableLogic
    private Boolean deleted;

    /**
     * 内容类别
     */
    private String type;
    /**
     * 内容状态
     */
    private String status;
    /**
     * 标签列表
     */
    private String tags;
    /**
     * 分类列表
     */
    private String categories;
    /**
     * 点击次数
     */
    private Integer hits;
    /**
     * 是否允许评论
     */
    private Boolean allowComment;
    /**
     * 是否允许ping
     */
    private Boolean allowPing;
    /**
     * 允许出现在聚合中
     */
    private Boolean allowFeed;

}
