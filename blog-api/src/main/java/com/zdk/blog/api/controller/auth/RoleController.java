package com.zdk.blog.api.controller.auth;


import com.github.pagehelper.PageInfo;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.model.auth.Role;
import com.zdk.blog.service.auth.PermissionService;
import com.zdk.blog.service.auth.RoleService;
import com.zdk.blog.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Api(tags = {"角色管理相关接口"})
@Controller
@RequestMapping(value = "/admin/roleManage")
public class RoleController extends CommonController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);

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

    @ApiOperation("新增角色或子角色")
    @PostMapping(value = "/add")
    @ResponseBody
    public ApiResponse add(@RequestParam(name = "name") String name, @RequestParam(name = "pid",required = false) Integer pid){
        return roleService.addRole(name,pid);
    }

    @ApiOperation("新增角色的弹出框")
    @GetMapping(value = "/addRoleForm")
    public String addRoleForm(){
        return "admin/auth/addRoleForm";
    }

    @ApiOperation("新增子角色的弹出框")
    @GetMapping(value = "/addChileRoleForm/{id}")
    public String addChileRoleForm(@PathVariable Integer id,Model model){
        model.addAttribute("pid",id);
        return "admin/auth/addRoleForm";
    }

    @ApiOperation("编辑角色的弹出框")
    @GetMapping(value = "/editRoleForm/{id}")
    public String editRoleForm(@PathVariable Integer id,Model model){
        Role role = roleService.getById(id);
        model.addAttribute("role",role);
        return "admin/auth/editRoleForm";
    }

    @ApiOperation("编辑角色")
    @PostMapping(value = "/edit")
    @ResponseBody
    public ApiResponse edit(Role role){
        return ApiResponse.result(roleService.saveOrUpdate(role));
    }

    @ApiOperation("删除角色")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ApiResponse delete(@RequestParam(name = "id")Integer id){
        return ApiResponse.result(roleService.removeById(id));
    }
}

