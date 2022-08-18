package com.zdk.blog.api.controller.admin;

import com.github.pagehelper.PageInfo;
import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.constant.ErrorConstant;
import com.zdk.blog.constant.SuccessConstant;
import com.zdk.blog.model.User;
import com.zdk.blog.service.UserService;
import com.zdk.blog.response.ApiResponse;
import com.zdk.blog.vo.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author zdk
 * @date 2021/8/31 21:28
 */
@Api(tags = {"用户管理相关接口"})
@Controller
@RequestMapping(value = "/admin/userManage")
public class UserManageController extends CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserManageController.class);

    @Autowired
    private UserService userService;


    @ApiOperation("用户管理页")
    @GetMapping(value = "")
    public String index(Model model,
                        @RequestParam(name = "page",required = false, defaultValue = "1") Integer pageNumber,
                        @RequestParam(name = "limit",required = false, defaultValue = "5")Integer pageSize,
                        @RequestParam(name = "keywords",required = false) String keywords){
        PageInfo<User> userPage = userService.getUserPage(pageNumber, pageSize, keywords);
        model.addAttribute("userPage",userPage);
        return "admin/userManage";
    }


    @ApiOperation("返回用户信息")
    @GetMapping(value = "/edit/{userId}")
    @ResponseBody
    public ApiResponse edit(@PathVariable Integer userId){
        User user = userService.getById(userId);
        return ApiResponse.result(user!=null,user);
    }

    @ApiOperation("更新用户信息")
    @PostMapping(value = "/update")
    @ResponseBody
    public ApiResponse update(UserInfoVO userInfoVo){
        return ApiResponse.result(userService.editUserInfo(userInfoVo),SuccessConstant.Common.SUCCESS,ErrorConstant.Common.UPDATE_FAIL);
    }

    @ApiOperation("删除用户")
    @PostMapping(value = "/delete")
    @ResponseBody
    public ApiResponse delete(Integer id){
        return ApiResponse.result(userService.removeById(id), SuccessConstant.Common.SUCCESS, ErrorConstant.Common.DELETE_FAIL);
    }
}
