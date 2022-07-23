package com.zdk.MyBlog.dto;

import lombok.*;

/**
 * @author zdk
 * @date 2021/11/18 20:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LinkDTO {
    /**
     * 文章id
     */
    private Integer id;
    /**
     * 链接标题
     */
    private String title;
    /**
     * 链接url
     */
    private String url;
    /**
     * 链接logo url
     */
    private String logo;
    /**
     * 链接描述
     */
    private String description;
    /**
     * 链接排序
     */
    private Integer sort;
}
