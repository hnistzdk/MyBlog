package com.zdk.MyBlog.controller.auth;


import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.model.pojo.auth.Permission;
import com.zdk.MyBlog.service.auth.PermissionService;
import com.zdk.MyBlog.service.auth.RoleService;
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
@Api("权限管理")
@Controller
@RequestMapping(value = "/admin/permissionManage",method = {RequestMethod.POST,RequestMethod.GET})
public class PermissionController extends BaseController {

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

