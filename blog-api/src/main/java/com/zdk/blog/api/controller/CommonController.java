package com.zdk.blog.api.controller;

import cn.hutool.json.JSONUtil;
import com.zdk.blog.constant.WebConst;
import com.zdk.blog.model.User;
import com.zdk.blog.utils.RedisUtil;
import com.zdk.blog.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zdk
 * @date 2021/10/28 18:43
 * 封装Controller层常用方法
 */
public class CommonController extends BaseController {

    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取当前登录用户
     * @return
     */
    public User getLoginUser(){
        Object user = redisUtil.hget(WebConst.USERINFO, TaleUtils.getCookieValue(WebConst.USERINFO, request));
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
