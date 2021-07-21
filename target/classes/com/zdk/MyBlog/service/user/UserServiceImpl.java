package com.zdk.MyBlog.service.user;

import com.zdk.MyBlog.mapper.UserMapper;
import com.zdk.MyBlog.model.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        if(StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            return null;
        }
        return userMapper.getUserInfoByCond(username, password);
    }
}
