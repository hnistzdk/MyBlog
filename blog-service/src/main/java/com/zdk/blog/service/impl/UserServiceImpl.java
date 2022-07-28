package com.zdk.blog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.common.model.User;
import com.zdk.blog.common.service.UserService;
import com.zdk.blog.common.utils.ParaValidator;
import com.zdk.blog.common.vo.UserInfoVO;
import com.zdk.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService,ParaValidator {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username) {
        if(notOk(username)){
            return null;
        }
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,username));
    }
    @Override
    public Boolean updateUserInfo(User user) {
        if(notOk(user)|| notOk(user.getId())){
            return false;
        }
        return userMapper.updateById(user)>0;
    }

    @Override
    public PageInfo<User> getUserPage(Integer pageNumber, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNumber, pageSize, "username");
        List<User> userList = lambdaQuery().like(isOk(keywords), User::getUsername, keywords)
                .or().like(isOk(keywords), User::getNickname, keywords)
                .or().like(isOk(keywords), User::getTrueName, keywords).list();
        return new PageInfo<>(userList);
    }

    @Override
    public Boolean editUserInfo(UserInfoVO userInfoVo) {
        if (userInfoVo==null|| notOk(userInfoVo.getId())){
            return false;
        }
        User user = new User();
        user.setId(userInfoVo.getId()).setNickname(userInfoVo.getNickname())
                .setGender(userInfoVo.getGender()).setTrueName(userInfoVo.getTrueName())
                .setEmail(userInfoVo.getEmail());
        return updateById(user);
    }
}
