package com.zdk.MyBlog.model.pojo;

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
    private Integer id;
    private String userId;
    private String authorName;
    private String title;
    private String time;
    private Integer read;
    private Integer comment;
    private String introduction;
    private String content;
}