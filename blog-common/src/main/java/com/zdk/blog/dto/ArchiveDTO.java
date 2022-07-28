package com.zdk.blog.dto;


import com.zdk.blog.model.Article;
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
public class ArchiveDTO {
    private String date;
    private String count;
    private List<Article> articles;
}
