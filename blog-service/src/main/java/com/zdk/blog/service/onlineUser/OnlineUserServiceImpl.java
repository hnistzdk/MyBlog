package com.zdk.blog.service.onlineUser;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.OnlineUserMapper;
import com.zdk.MyBlog.model.OnlineUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zdk
 * @date 2021/8/12 22:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class OnlineUserServiceImpl extends ServiceImpl<OnlineUserMapper, OnlineUser> implements OnlineUserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineUserServiceImpl.class);
    @Autowired
    private OnlineUserMapper onlineUserMapper;
}
