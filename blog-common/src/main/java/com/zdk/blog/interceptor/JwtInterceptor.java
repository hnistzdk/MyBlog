package com.zdk.blog.interceptor;

import com.zdk.blog.config.JwtConfig;
import com.zdk.blog.constant.AuthConstant;
import com.zdk.blog.exception.BusinessException;
import com.zdk.blog.model.User;
import com.zdk.blog.security.Payload;
import com.zdk.blog.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 14:06
 */
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtConfig jwtConfig;

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        if (request.getRequestURI().startsWith("/favicon.ico") || request.getRequestURI().startsWith("/error")){
            return true;
        }
        String token = request.getHeader(AuthConstant.HEADER_AUTHORIZATION);
        if (token == null) {
            throw new BusinessException("token为空");
        }
        // 1.校验JWT字符串
        try {
            JwtUtils.checkToken(token, jwtConfig.getRsaPublicKey());
        }catch (SignatureException e){
            throw new BusinessException("token验证失败");
        }catch (ExpiredJwtException e){
            throw new BusinessException("token已过期");
        }catch (Exception e){
            throw new BusinessException("token无效");
        }
        // 2.取出JWT字符串载荷中的随机token，获取用户信息
        Payload<User> userPayload = JwtUtils.getInfoFromToken(token, jwtConfig.getRsaPublicKey(), User.class);
        return true;
    }
}
