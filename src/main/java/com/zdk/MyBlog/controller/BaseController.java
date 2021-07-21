package com.zdk.MyBlog.controller;

import cn.hutool.json.JSONUtil;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zdk
 * @date 2021/7/21 9:38
 */
public class BaseController {

    @Autowired
    RedisUtil redisUtil;

    public User getLoginUser(){
        Object user = redisUtil.hget(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);
        return JSONUtil.toBean(JSONUtil.parseObj(user), User.class);
    }
}
