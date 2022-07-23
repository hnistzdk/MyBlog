package com.zdk.blog.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.blog.mapper.auth.RolePermissionMapper;

import com.zdk.blog.service.auth.RolePermissionService;
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
