package com.zdk.blog.api.controller.user;

import cn.hutool.core.date.DateUtil;
import com.zdk.blog.api.controller.BaseController;

import com.zdk.blog.api.controller.CommonController;
import com.zdk.blog.common.constant.LogActions;
import com.zdk.blog.common.constant.WebConst;
import com.zdk.blog.common.model.Logs;
import com.zdk.blog.common.model.User;
import com.zdk.blog.common.utils.ApiResponse;
import com.zdk.blog.common.utils.IpKit;
import com.zdk.blog.common.utils.RedisUtil;
import com.zdk.blog.common.utils.TaleUtils;
import com.zdk.blog.common.service.LogsService;
import com.zdk.blog.common.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

/**
 * @author zdk
 * @date 2021/7/20 17:49
 */
@Api("登录相关接口")
@Controller
@RequestMapping(value = "/user")
public class LoginController extends CommonController {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private LogsService logsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Integer ERROR_NUMBER = 4;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);


    @PostMapping(value = "/login")
    @ResponseBody
    public ApiResponse login(@RequestParam(name = "username",required = false) String username,
                             @RequestParam(name = "password",required = false) String password,
                             @RequestParam(name = "rememberMe",required = false) @Nullable String remember) {
        String ip = IpKit.getIpAddressByRequest(request);
        //构造登录错误次数唯一缓存key
        String userCountKey = WebConst.LOGIN_ERROR_COUNT + ip + username;
        //构造登录成功后用户信息唯一缓存key
        String userInfoKey = WebConst.USERINFO + ip + username;
        Integer loginErrorCount = redisUtil.getNumber(userCountKey);
        if (isOk(loginErrorCount) && loginErrorCount >= ERROR_NUMBER) {
            return ApiResponse.fail("您输入密码已经错误超过4次，请10分钟后尝试");
        }
        User user = userService.login(username);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                userService.updateUserInfo(user.setLoginDate(DateUtil.now()).setLoginTimes(user.getLoginTimes()+1));
                Logs logs = new Logs().setAction(LogActions.LOGIN.getAction()).setAuthorId(user.getId())
                        .setCreateTime(DateUtil.now()).setIp(IpKit.getIpAddressByRequest(request));
                logsService.save(logs);
                redisUtil.del(userCountKey);
                Cookie cookie = new Cookie(WebConst.USERINFO,userInfoKey);
//                cookie.setMaxAge(3000);//最大有效时间
                cookie.setPath("/");
                response.addCookie(cookie);

                if (isOk(remember)) {
                    redisUtil.hset(WebConst.USERINFO, cookie.getValue(), user, 3600);
                } else {
                    redisUtil.hset(WebConst.USERINFO, cookie.getValue(), user, -1);
                }
                return ApiResponse.success("登录成功");
            } else {
                loginErrorCount = redisUtil.getNumber(userCountKey);
                if (notOk(loginErrorCount)) {
                    redisUtil.setNumber(userCountKey, 1, 600);
                } else {
                    redisUtil.incr(userCountKey, 1);
                    if (redisUtil.getNumber(userCountKey) >= ERROR_NUMBER) {
                        redisUtil.expire(userCountKey, 600);
                    }
                }
                return ApiResponse.fail("账号或密码错误,您还有" + (ERROR_NUMBER - redisUtil.getNumber(userCountKey)) + "次机会");
            }
        } else {
            return ApiResponse.fail("该用户不存在");
        }
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
