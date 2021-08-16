package com.zdk.MyBlog.interceptor;

import com.zdk.MyBlog.constant.WebConst;
import com.zdk.MyBlog.utils.IpKit;
import com.zdk.MyBlog.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zdk
 * @date 2021/7/6 19:48
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {
    private static final String USER_AGENT = "user-agent";
    private static final Logger logger = LoggerFactory.getLogger(LoginHandlerInterceptor.class);
    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        logger.info("===========>进入拦截器");
        String uri=request.getRequestURI();
        logger.info("UserAgent: {}",request.getHeader(USER_AGENT));
        logger.info("用户访问地址: {}, 来路地址: {}",uri, IpKit.getIpAddressByRequest(request));

        //请求拦截处理
        Object user = redisUtil.hget(WebConst.USERINFO,WebConst.LOGIN_SESSION_KEY);

        if (user==null&&uri.startsWith("/user") && !uri.startsWith("/user/login")) {
            request.setAttribute("msg", "请先登录");
            request.getRequestDispatcher("/user/toLogin").forward(request, response);
            return false;
        }
        if (user==null&&uri.startsWith("/admin") && !uri.startsWith("/admin/login")
                && !uri.startsWith("/admin/css") && !uri.startsWith("/admin/images")
                && !uri.startsWith("/admin/js") && !uri.startsWith("/admin/plugins")
                && !uri.startsWith("/admin/editormd")) {
            request.setAttribute("msg", "请先登录");
            request.getRequestDispatcher("/admin/login").forward(request, response);
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
