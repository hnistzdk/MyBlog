package com.zdk.MyBlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdk.MyBlog.model.Comments;
import org.springframework.stereotype.Repository;

/**
 * @author zdk
 * @date 2021/8/12 22:29
 */
@Repository
public interface CommentsMapper extends BaseMapper<Comments> {
}
