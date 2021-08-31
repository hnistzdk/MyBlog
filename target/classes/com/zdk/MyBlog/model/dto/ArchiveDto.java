package com.zdk.MyBlog.model.dto;

import com.zdk.MyBlog.model.pojo.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;


/**
 * 文章归档类
 * @author zdk
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class ArchiveDto {
    private String date;
    private String count;
    private List<Article> articles;
}
