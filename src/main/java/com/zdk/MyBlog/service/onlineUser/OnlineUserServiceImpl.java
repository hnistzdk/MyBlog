package com.zdk.MyBlog.service.onlineUser;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.OnlineUserMapper;
import com.zdk.MyBlog.model.pojo.OnlineUser;
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
    @Autowired
    private OnlineUserMapper onlineUserMapper;
}
