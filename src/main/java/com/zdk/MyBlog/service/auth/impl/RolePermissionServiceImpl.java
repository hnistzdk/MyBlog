package com.zdk.MyBlog.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.MyBlog.mapper.auth.RolePermissionMapper;
import com.zdk.MyBlog.model.auth.RolePermission;
import com.zdk.MyBlog.service.auth.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  角色权限关联服务实现类
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
