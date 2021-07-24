package com.zdk.MyBlog.interceptor;

import cn.hutool.json.JSONUtil;
import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.model.pojo.User;
import com.zdk.MyBlog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.geom.RectangularShape;

/**
 * @author zdk
 * @date 2021/7/6 19:48
 */
@Slf4j
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("===========>进入拦截器");
        Object user = redisUtil.hget(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);
        if(user==null){
            request.setAttribute("msg", "请先登录");
            request.getRequestDispatcher("/user/toLogin").forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
