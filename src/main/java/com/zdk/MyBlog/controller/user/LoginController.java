package com.zdk.MyBlog.controller.user;

import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.controller.BaseController;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.service.user.UserService;
import com.zdk.MyBlog.utils.ApiResponse;
import com.zdk.MyBlog.utils.IpKit;
import com.zdk.MyBlog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping(value ="/toLogin")
    public String toLogin(){
        return "login";
    }

    @GetMapping(value = "/toIndex")
    public String toIndex(){
        System.out.println("==========>进入toIndex方法");
        return "index";
    }


    @PostMapping(value = "/userLogin")
    @ResponseBody
    public ApiResponse login(@RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "rememberMe") @Nullable String remember, HttpServletRequest request) {

        String ip=IpKit.getIpAddrByRequest(request);
        //构造登录错误次数唯一缓存key
        String userCountKey=WebConst.LOGIN_ERROR_COUNT+ip+username;
        System.out.println("userCountKey = " + userCountKey);
        //构造登录成功后用户信息唯一缓存key
        String userInfoKey=WebConst.USERINFO+ip+username;
        System.out.println("userInfoKey = " + userInfoKey);

        Long loginErrorCount = redisUtil.getNumber(userCountKey);
        if(loginErrorCount!=null&&loginErrorCount>=3){
            return ApiResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
        }
        User user = userService.login(username, password);
        if(user!=null){
            redisUtil.del(userCountKey);
            if(remember!=null){
                redisUtil.hset(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY, user, 3600);
            }else{
                redisUtil.hset(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY, user, -1);
            }
            return ApiResponse.success();
        }else{
            loginErrorCount = redisUtil.getNumber(userCountKey);
            if(loginErrorCount==null){
                redisUtil.setNumber(userCountKey,1,600);
            }else{
                redisUtil.incr(userCountKey, 1);
                if(redisUtil.getNumber(userCountKey)>=3){
                    redisUtil.expire(userCountKey,600);
                }
            }
            return ApiResponse.fail("账号或密码错误,您还有"+(3-redisUtil.getNumber(userCountKey))+"次机会");
        }
    }

    @GetMapping("/logout")
    public String logout(){
        redisUtil.hdel(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);
        return "/login";
    }
}
