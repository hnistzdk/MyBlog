package com.zdk.MyBlog.service.user;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.mapper.UserMapper;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.model.vo.UserInfoVo;
import com.zdk.MyBlog.utils.ParaValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

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

    @Override
    public PageInfo<User> getUserPage(Integer pageNumber, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNumber, pageSize, "username");
        List<User> userList = lambdaQuery().like(ParaValidator.isOk(keywords), User::getUsername, keywords)
                .or().like(ParaValidator.isOk(keywords), User::getNickname, keywords)
                .or().like(ParaValidator.isOk(keywords), User::getTrueName, keywords).list();
        return new PageInfo<>(userList);
    }

    @Override
    public Boolean editUserInfo(UserInfoVo userInfoVo) {
        if (userInfoVo==null||ParaValidator.notOk(userInfoVo.getId())){
            return false;
        }
        User user = new User();
        user.setId(userInfoVo.getId()).setNickname(userInfoVo.getNickname())
                .setGender(userInfoVo.getGender()).setTrueName(userInfoVo.getTrueName())
                .setEmail(userInfoVo.getEmail());
        return updateById(user);
    }
}
