package com.zdk.blog.vo.article;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.zdk.blog.model.Article;
import com.zdk.blog.model.Comments;
import com.zdk.blog.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author zdk
 * @Date 2022/10/5 15:45
 */
@Data
public class ArticleVO extends BaseVO {
    private Integer id;

    private Integer authorId;

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

    /**
     * 点击次数
     */
    private Integer hits;
    /**
     * 是否允许评论
     */
    private Boolean allowComment;


    private List<Comments> commentsList;
}
