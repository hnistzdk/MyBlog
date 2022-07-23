package com.zdk.blog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


/**
 *
 * 后台统计对象
 * @author zdk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class StatisticsDTO {
    /**
     * 文章数
     */
    private Long articles;
    /**
     * 评论数
     */
    private Long comments;
    /**
     * 连接数
     */
    private Long links;
    /**
     * 附件数
     */
    private Long attaches;
}
