package com.zdk.blog.common.service.auth;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.common.model.auth.Role;
import com.zdk.blog.common.utils.ApiResponse;

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
     * @param pid
     * @return
     */
    ApiResponse addRole(String name,Integer pid);
}
