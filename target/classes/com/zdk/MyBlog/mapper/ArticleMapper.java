package com.zdk.MyBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdk.MyBlog.model.pojo.Article;
import org.springframework.stereotype.Repository;

/**
 * @author zdk
 * @date 2021/7/22 17:07
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

}
