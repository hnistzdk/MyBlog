package com.zdk.MyBlog.service.auth.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.mapper.auth.PermissionMapper;
import com.zdk.MyBlog.model.pojo.auth.Permission;
import com.zdk.MyBlog.service.auth.PermissionService;
import com.zdk.MyBlog.utils.ParaValidatorUtil;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public PageInfo<Permission> getPermissionPage(Integer pageNumber, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNumber, pageSize, "id");
        List<Permission> permissionList = lambdaQuery().like(ParaValidatorUtil.isOk(keywords), Permission::getPermissionKey, keywords).list();
        return new PageInfo<>(permissionList);
    }
}
