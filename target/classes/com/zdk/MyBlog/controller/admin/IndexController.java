package com.zdk.MyBlog.controller.admin;

import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.utils.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author zdk
 * @date 2021/8/12 20:53
 */
@Api("后台首页")
@Controller
@RequestMapping("/admin")
public class IndexController extends BaseController {
    @ApiOperation("进入首页")
    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }

    /**
     * 个人设置页面
     */
    @GetMapping(value = "/profile")
    public String profile() {
        return "admin/profile";
    }

    /**
     * 保存个人信息
     */
    @PostMapping(value = "/profile")
    @ResponseBody
    public ApiResponse saveProfile(@RequestParam String screenName, @RequestParam String email, HttpServletRequest request, HttpSession session) {
        return null;
    }

    /**
     * 修改密码
     */
    @PostMapping(value = "/password")
    @ResponseBody
    public ApiResponse modifyPassword() {
        return null;
    }

}
