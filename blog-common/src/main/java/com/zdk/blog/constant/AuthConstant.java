package com.zdk.blog.constant;

/**
 * @Description
 * @Author zdk
 * @Date 2022/10/3 12:30
 */
public interface AuthConstant {
    /**
     * =============登录相关=================
     */
    Integer ERROR_NUMBER = 4;
    String USER_INFO_CACHE_KEY_PREFIX = "user:";


    /**
     * =============jwt相关=================
     */
    String HEADER_AUTHORIZATION = "Authorization";
    String JWT_PAYLOAD_USER_KEY = "user";
    /**
     * 允许的过期校验的时钟偏差秒(实际的过期时间=当前过期时间+这个变量)
     */
    Long ALLOWED_CLOCK_SKEW_SECONDS = 1L;
}
