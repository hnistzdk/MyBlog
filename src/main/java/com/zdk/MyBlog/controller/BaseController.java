package com.zdk.MyBlog.controller;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zdk
 * @date 2021/7/21 9:38
 * Controller层基础封装 基于CommonController
 */
public class BaseController extends CommonController{
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    public HttpServletRequest request;
    @Autowired
    public HttpServletResponse response;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public BaseController() {
    }

    public User getLoginUser(){
        Object user = redisUtil.hget(WebConst.USERINFO,TaleUtils.getCookieValue(WebConst.USERINFO, request));
        return JSONUtil.toBean(JSONUtil.parseObj(user), User.class);
    }
}
