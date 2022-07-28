package com.zdk.blog.common.service.auth;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.common.model.auth.Permission;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 权限管理分页
     * @param pageNumber
     * @param pageSize
     * @param keywords
     * @return
     */
    PageInfo<Permission> getPermissionPage(Integer pageNumber, Integer pageSize, String keywords);
}
