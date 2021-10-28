package com.zdk.MyBlog.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.mapper.auth.RoleMapper;
import com.zdk.MyBlog.model.pojo.auth.Role;
import com.zdk.MyBlog.service.auth.RoleService;
import com.zdk.MyBlog.utils.ParaValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  角色实现类服务实现类
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements RoleService{
    @Autowired
    private ParaValidatorUtil paraValidatorUtil;

    @Override
    public PageInfo<Role> getRolePage(Integer pageNumber, Integer pageSize, String keywords) {
        PageHelper.startPage(pageNumber, pageSize, "id");
        List<Role> roleList = lambdaQuery().like(paraValidatorUtil.isOk(keywords), Role::getName, keywords).list();
        return new PageInfo<>(roleList);
    }

    @Override
    public Boolean addRole(String name) {
        return saveOrUpdate(new Role().setName(name), lambdaUpdate().ne(Role::getName, name));
    }
}
