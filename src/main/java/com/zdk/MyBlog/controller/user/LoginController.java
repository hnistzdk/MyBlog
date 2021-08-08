package com.zdk.MyBlog.controller.user;

import cn.hutool.core.date.DateUtil;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.user.UserService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.IpKit;
import com.zdk.MyBlog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zdk
 * @date 2021/7/20 17:49
 */
@Slf4j
@Controller
@RequestMapping(value ="/user")
public class LoginController extends BaseController {

    @Autowired
    RedisUtil redisUtil;
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping(value = "/userLogin")
    @ResponseBody
    public ApiResponse login(@RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "rememberMe") @Nullable String remember, HttpServletRequest request) {
        String ip=IpKit.getIpAddrByRequest(request);
        //构造登录错误次数唯一缓存key
        String userCountKey=WebConst.LOGIN_ERROR_COUNT+ip+username;
        //构造登录成功后用户信息唯一缓存key
        String userInfoKey=WebConst.USERINFO+ip+username;

        Integer loginErrorCount = redisUtil.getNumber(userCountKey);
        if(isOk(loginErrorCount)&&loginErrorCount>=3){
            return ApiResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
        }
        if(isOk(username)){
            User user = userService.login(username);
            if(user!=null){
                if(passwordEncoder.matches(password, user.getPassword())){
                    user.setLoginDate(DateUtil.now());
                    userService.updateUserInfo(user);
                    redisUtil.del(userCountKey);
                    if(isOk(remember)){
                        redisUtil.hset(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY, user, 3600);
                    }else{
                        redisUtil.hset(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY, user, -1);
                    }
                    return ApiResponse.success();
                }else{
                    loginErrorCount = redisUtil.getNumber(userCountKey);
                    if(isOk(loginErrorCount)){
                        redisUtil.setNumber(userCountKey,1,600);
                    }else{
                        redisUtil.incr(userCountKey, 1);
                        if(redisUtil.getNumber(userCountKey)>=3){
                            redisUtil.expire(userCountKey,600);
                        }
                    }
                    return ApiResponse.fail("账号或密码错误,您还有"+(3-redisUtil.getNumber(userCountKey))+"次机会");
                }
            }else{
                return ApiResponse.fail("该用户不存在");
            }
        }else{
            return ApiResponse.fail("请输入合法的用户名");
        }
    }
    @GetMapping("/logout")
    public String logout(){
        redisUtil.hdel(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);
        return "/login";
    }
}
