package com.zdk.blog.api.controller;

import cn.hutool.json.JSONUtil;

import com.zdk.blog.common.constant.WebConst;
import com.zdk.blog.common.model.User;
import com.zdk.blog.common.utils.ParaValidator;
import com.zdk.blog.common.utils.RedisUtil;
import com.zdk.blog.common.utils.TaleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.Cookie;

/**
 * @author zdk
 * @date 2021/7/21 9:38
 * Controller层基础封装 基于CommonController
 */
public abstract class BaseController implements ParaValidator {

}
