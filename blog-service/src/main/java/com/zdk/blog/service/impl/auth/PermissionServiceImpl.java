package com.zdk.blog.service.impl.auth;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.common.model.auth.Permission;
import com.zdk.blog.common.service.auth.PermissionService;
import com.zdk.blog.common.utils.ParaValidator;
import com.zdk.blog.mapper.auth.PermissionMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  权限服务实现类
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService,ParaValidator {
    @Override
    public PageInfo<Permission> getPermissionPage(Integer pageNumber, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNumber, pageSize, "id");
        List<Permission> permissionList = lambdaQuery().like(isOk(keywords), Permission::getPermissionKey, keywords).list();
        return new PageInfo<>(permissionList);
    }
}
