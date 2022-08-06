package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.blog.model.OnlineUser;
import com.zdk.blog.service.OnlineUserService;
import com.zdk.blog.utils.ParaValidator;
import com.zdk.blog.mapper.OnlineUserMapper;
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
public class OnlineUserServiceImpl extends ServiceImpl<OnlineUserMapper, OnlineUser> implements OnlineUserService, ParaValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(OnlineUserServiceImpl.class);

}
