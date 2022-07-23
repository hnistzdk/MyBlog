package com.zdk.blog.service.auth.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zdk.blog.common.constant.ErrorConstant;
import com.zdk.blog.mapper.auth.RoleMapper;
import com.zdk.blog.common.model.auth.Role;
import com.zdk.blog.common.utils.ParaValidatorUtil;
import com.zdk.blog.service.auth.RoleService;
import com.zdk.blog.common.utils.ApiResponse;
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
    public ApiResponse addRole(String name,Integer pid) {
        if (paraValidatorUtil.notOk(name)){
            return ApiResponse.fail(ErrorConstant.Common.INVALID_PARAM);
        }
        Integer count = lambdaQuery().eq(Role::getName, name).count();
        if (count != 0) {
            return ApiResponse.fail(ErrorConstant.Role.ROLE_IS_EXIST);
        }
        if (pid == null){
            return ApiResponse.result(save(new Role().setName(name)));
        }
        return ApiResponse.result(save(new Role().setName(name).setPid(pid)));
    }
}
