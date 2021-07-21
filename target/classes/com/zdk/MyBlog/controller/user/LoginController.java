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
        return "index";
    }


    @PostMapping(value = "/userLogin")
    @ResponseBody
    public ApiResponse login(@RequestParam(name = "username") String username,
                             @RequestParam(name = "password") String password, HttpServletRequest request) {

        String ip=IpKit.getIpAddrByRequest(request);
        //构造登录错误次数唯一缓存key
        String userCountKey=WebConst.LOGIN_ERROR_COUNT+ip+username;
        //构造登录成功后用户信息唯一缓存key
        String userInfoKey=WebConst.USERINFO+ip+username;

        String loginErrorCount = redisUtil.get(userCountKey);
        if(loginErrorCount!=null&&Integer.parseInt(loginErrorCount)>=3){
            return ApiResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
        }
        User user = userService.login(username, password);
        if(user!=null){
            redisUtil.del(userCountKey);
            redisUtil.hset(WebConst.USERINFO,userInfoKey, user, 6000);
            return ApiResponse.success();
        }else{
            loginErrorCount = redisUtil.get(userCountKey);
            if(loginErrorCount==null){
                redisUtil.set(userCountKey,"1");
            }else{
                redisUtil.incr(userCountKey, 1);
                if(Integer.parseInt(redisUtil.get(userCountKey))>=3){
                    redisUtil.expire(userCountKey,600);
                }
            }
            return ApiResponse.fail("账号或密码错误,您还有"+(3-Integer.parseInt(redisUtil.get(userCountKey)))+"次机会");
        }
    }
}
