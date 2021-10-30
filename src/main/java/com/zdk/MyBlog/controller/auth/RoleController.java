package com.zdk.MyBlog.controller.auth;


import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.model.pojo.auth.Role;
import com.zdk.MyBlog.service.auth.PermissionService;
import com.zdk.MyBlog.service.auth.RoleService;
import com.zdk.MyBlog.utils.ApiResponse;
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
@Api("角色管理")
@Controller
@RequestMapping(value = "/admin/roleManage",method = {RequestMethod.POST,RequestMethod.GET})
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("角色管理页")
    @GetMapping(value = "")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(name = "limit",required = false, defaultValue = "10")Integer pageSize,
                        @RequestParam(name = "keywords",required = false) String keywords){
        PageInfo<Role> rolePage = roleService.getRolePage(pageNumber, pageSize, keywords);
        model.addAttribute("rolePage",rolePage);
        model.addAttribute("user",getLoginUser());
        return "admin/auth/roleManage";
    }

    @ApiOperation("新增角色")
    @PostMapping(value = "/add")
    @ResponseBody
    public ApiResponse add(@RequestParam(name = "name") String name){
        return roleService.addRole(name);
    }

    @ApiOperation("新增角色的弹出框")
    @GetMapping(value = "/addRoleForm")
    public String addRoleForm(@RequestParam(name = "name",required = false) String name){
        return "admin/auth/addRoleForm";
    }
}

