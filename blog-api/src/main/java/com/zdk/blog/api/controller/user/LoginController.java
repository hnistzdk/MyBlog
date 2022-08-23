package com.zdk.blog.api.controller.user;

import cn.hutool.core.date.DateUtil;

import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.api.controller.auth.RolePermissionController;
import com.zdk.blog.constant.LogActions;
import com.zdk.blog.constant.WebConst;
import com.zdk.blog.model.Logs;
import com.zdk.blog.model.User;
import com.zdk.blog.request.login.LoginRequest;
import com.zdk.blog.response.ApiResponse;
import com.zdk.blog.utils.IpKit;
import com.zdk.blog.utils.RedisUtil;
import com.zdk.blog.utils.TaleUtils;
import com.zdk.blog.service.LogsService;
import com.zdk.blog.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

/**
 * @author zdk
 * @date 2021/7/20 17:49
 */
@Api(tags = {"登录相关相关接口"})
@Controller
@RequestMapping(value = "/user")
public class LoginController extends CommonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private LogsService logsService;


    @PostMapping(value = "/login")
    @ResponseBody
    public ApiResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping("/logout")
    public String logout() {
        Logs logs = new Logs().setAction(LogActions.LOGOUT.getAction()).setAuthorId(getLoginUser().getId())
                .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
        logsService.save(logs);
        redisUtil.hdel(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request));
        return "blog/login";
    }
}
