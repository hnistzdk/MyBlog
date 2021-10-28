package com.zdk.MyBlog.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.model.pojo.auth.Role;
import com.zdk.MyBlog.utils.ApiResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
public interface RoleService extends IService<Role> {
    /**
     * 角色管理分页
     * @param pageNumber
     * @param pageSize
     * @param keywords
     * @return
     */
    PageInfo<Role> getRolePage(Integer pageNumber, Integer pageSize, String keywords);

    /**
     * 根据角色名称添加角色
     * @param name
     * @return
     */
    ApiResponse addRole(String name);
}
