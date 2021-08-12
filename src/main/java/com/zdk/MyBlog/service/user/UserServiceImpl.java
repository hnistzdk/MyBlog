package com.zdk.MyBlog.service.user;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.UserMapper;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.utils.ParaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(String username) {
        if(ParaValidator.notOk(username)){
            return null;
        }
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,username));
    }
    @Override
    public Boolean updateUserInfo(User user) {
        if(ParaValidator.notOk(user)||ParaValidator.notOk(user.getId())){
            return false;
        }
        return userMapper.updateById(user)>0;
    }
}
