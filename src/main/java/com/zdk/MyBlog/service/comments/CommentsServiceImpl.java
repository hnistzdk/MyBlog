package com.zdk.MyBlog.service.comments;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.CommentsMapper;
import com.zdk.MyBlog.model.pojo.Comments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zdk
 * @date 2021/8/12 22:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {
    @Autowired
    private CommentsMapper commentsMapper;
}
