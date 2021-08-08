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
     * 根据用户名获取用户信息
     * @param username
     * @return
     */
    User getUserInfoByCond(@Param("username") String username);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);
}
