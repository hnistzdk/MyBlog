package com.zdk.MyBlog.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zdk.MyBlog.constant.ErrorConstant;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.model.vo.UserInfoVo;
import com.zdk.MyBlog.service.user.UserService;
import com.zdk.MyBlog.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.spring5.processor.SpringUErrorsTagProcessor;

/**
 * @author zdk
 * @date 2021/8/31 21:28
 */
@Api("用户管理")
@Controller
@RequestMapping("/admin/userManage")
public class UserManageController extends BaseController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户管理页")
    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize,
                        @RequestParam(name = "keywords",required = false) String keywords){
        PageInfo<User> userPage = userService.getUserPage(pageNumber, pageSize, keywords);
        model.addAttribute("userPage",userPage);
        return "admin/userManage";
    }


    @ApiOperation("返回用户信息")
    @GetMapping("/edit/{userId}")
    @ResponseBody
    public ApiResponse edit(@PathVariable Integer userId){
        User user = userService.getById(userId);
        if (user!=null){
            return ApiResponse.success(user);
        }
        return ApiResponse.fail("查无此人");
    }

    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    @ResponseBody
    public ApiResponse update(UserInfoVo userInfoVo){
        if (userService.editUserInfo(userInfoVo)){
            return ApiResponse.success();
        }
        return ApiResponse.fail("操作失败");
    }
}
