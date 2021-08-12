package com.zdk.MyBlog.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdk.MyBlog.model.pojo.User;
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
}
