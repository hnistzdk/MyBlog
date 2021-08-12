package com.zdk.MyBlog.model.pojo;

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
public class Article implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userId;
    private String authorName;
    private String title;
    @TableField(fill = FieldFill.INSERT)
    private String publicTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateTime;
    private Integer readCount;
    private Integer commentCount;
    private String introduction;
    private String content;
    @TableLogic
    private Boolean deleted;
}
