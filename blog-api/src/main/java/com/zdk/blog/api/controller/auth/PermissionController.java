package com.zdk.blog.api.controller.auth;


import com.github.pagehelper.PageInfo;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.model.auth.Permission;
import com.zdk.blog.service.auth.PermissionService;
import com.zdk.blog.service.auth.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zdk
 * @since 2021-10-28
 */
@Api(tags = {"权限管理相关接口"})
@Controller
@RequestMapping(value = "/admin/permissionManage")
public class PermissionController extends CommonController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @ApiOperation("权限管理页")
    @GetMapping(value = "")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(name = "limit",required = false, defaultValue = "10")Integer pageSize,
                        @RequestParam(name = "keywords",required = false) String keywords){
        PageInfo<Permission> permissionPage = permissionService.getPermissionPage(pageNumber, pageSize, keywords);
        model.addAttribute("permissionPage",permissionPage);
        return "admin/auth/permissionManage";
    }
}

