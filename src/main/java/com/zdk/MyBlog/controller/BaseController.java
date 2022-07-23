package com.zdk.MyBlog.controller;

import cn.hutool.json.JSONUtil;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.User;
import com.zdk.MyBlog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;

/**
 * @author zdk
 * @date 2021/7/21 9:38
 * Controller层基础封装 基于CommonController
 */
public class BaseController extends CommonController{
    @Autowired
    private RedisUtil redisUtil;
    public BaseController() {
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public User getLoginUser(){
        Object user = redisUtil.hget(WebConst.USERINFO,TaleUtils.getCookieValue(WebConst.USERINFO, request));
        return JSONUtil.toBean(JSONUtil.parseObj(user), User.class);
    }

    /**
     * 设置cookie
     * @param name
     * @param value
     * @param maxAge
     */
    public void setCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setSecure(false);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
