package com.zdk.MyBlog.service.user;

import com.zdk.MyBlog.model.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author zdk
 * @date 2021/7/20 18:24
 */
@Service
public interface UserService {
    /**
     * 根据用户名和密码获取用户信息
     * @param username
     * @param password
     * @return
     */
    User login(@Param("username") String username, @Param("password") String password);
}
