package com.zdk.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.model.User;
import com.zdk.blog.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User login(@Param("username") String username);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Boolean updateUserInfo(User user);

    /**
     * 根据关键词搜索获取用户信息
     * @param pageNumber
     * @param pageSize
     * @param keywords
     * @return
     */
    PageInfo<User> getUserPage(Integer pageNumber, Integer pageSize, String keywords);

    /**
     * 管理员更新用户信息
     * @param userInfoVo
     * @return
     */
    Boolean editUserInfo(UserInfoVO userInfoVo);
}
