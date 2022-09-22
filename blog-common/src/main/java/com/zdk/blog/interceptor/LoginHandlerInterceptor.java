package com.zdk.blog.interceptor;

import com.zdk.blog.utils.IpKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

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


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri=request.getRequestURI();
        logger.info("UserAgent: {}",request.getHeader(USER_AGENT));
        logger.info("用户访问地址: {}, 来路地址: {}",uri, IpKit.getIpAddressByRequest(request));

        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //先检查类上有没有Uncheck注解 如果有 放行
            Class<?> beanType = handlerMethod.getBeanType();
            Uncheck classAnnotation = beanType.getAnnotation(Uncheck.class);
            if (classAnnotation != null){
                return true;
            }

            //再检查方法上有没有 如果有 放行
            Uncheck uncheck = handlerMethod.getMethodAnnotation(Uncheck.class);
            if (uncheck != null){
                return true;
            }
        }

        if (uri.startsWith("/webhook/shell")){
            logger.info("执行shell脚本,放行");
            return true;
        }

        //用于api文档
        if (uri.startsWith("/swagger-resources") || uri.startsWith("/v2/api-docs")|| uri.startsWith("/favicon.ico")){
            logger.info("api文档,放行");
            return true;
        }
        return true;
    }
}
