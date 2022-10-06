package com.zdk.blog.request.article;

import com.zdk.blog.request.common.BaseCreateRequest;
import lombok.Data;

/**
 * @Description
 * @Author zdk
 * @Date 2022/10/5 15:58
 */
@Data
public class ArticleCreateRequest extends BaseCreateRequest {
    /**
     * 标题内容
     */
    private String title;

    /**
     * 标签列表
     */
    private String tags;


    /**
     * 分类列表
     */
    private String categories;

    /**
     * 内容主体
     */
    private String content;
    /**
     * 文章纯文本格式(渲染编辑页面时使用)
     */
    private String text;

    /**
     * 是否允许评论
     */
    private Boolean allowComment;
}
