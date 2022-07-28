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
 * 日志表
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
@TableName("blog_logs")
public class Logs implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 事件
     */
    private String action;

    /**
     * 数据
     */
    private String data;

    /**
     * 作者编号
     */
    @TableField("author_id")
    private Integer authorId;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 产生时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;


}
