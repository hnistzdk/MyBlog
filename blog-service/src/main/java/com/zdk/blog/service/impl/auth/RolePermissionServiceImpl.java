package com.zdk.blog.service.impl.auth;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zdk.blog.common.model.auth.RolePermission;
import com.zdk.blog.common.utils.ParaValidator;
import com.zdk.blog.mapper.auth.RolePermissionMapper;

import com.zdk.blog.common.service.auth.RolePermissionService;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService, ParaValidator {

}
