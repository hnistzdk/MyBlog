package com.zdk.blog.constant;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/7/21 9:40
 */
@Component
public class WebConst {
    /**
     * 一些网站配置
     */
    public static final Map<String, String> initConfig = new HashMap<>();

    /**
     * session的key
     */
    public static final String LOGIN_SESSION_KEY = "login_user";

    public static final String LOGIN_ERROR_COUNT="login_error_count";

    public static final String USERINFO="userInfo";

    public static final String USER_IN_COOKIE = "S_L_ID";

    public static final String COMMENT_INTERVAL = "comment_interval";




    /**
     * aes加密加盐
     */
    public static final String AES_SALT = "0123456789abcdef";

    /**
     * 最大获取文章条数
     */
    public static final int MAX_POSTS = 9999;

    /**
     * 最大页码
     */
    public static final int MAX_PAGE = 100;

    /**
     * 文章最多可以输入的文字数
     */
    public static final int MAX_TEXT_COUNT = 200000;

    /**
     * 文章标题最多可以输入的文字个数
     */
    public static final int MAX_TITLE_COUNT = 200;

    /**
     * 点击次数超过多少更新到数据库
     */
    public static final int HIT_EXCEED = 10;

    /**
     * 上传文件最大1M
     */
    public static final Integer MAX_FILE_SIZE = 1048576;
}
