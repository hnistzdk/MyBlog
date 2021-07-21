package com.zdk.MyBlog.mapper;

import com.zdk.MyBlog.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author zdk
 * @date 2021/7/20 18:22
 */
@Mapper
public interface UserMapper {
    /**
     * 根据用户名和密码获取用户信息
     * @param username
     * @param password
     * @return
     */
    User getUserInfoByCond(@Param("username") String username, @Param("password") String password);
}
