package com.zdk.blog.security;

import java.io.Serializable;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 10:58
 */
public interface IPassport extends Serializable {
    /**
     * 获取用户id
     * @return
     */
    Object getUserId();

    /**
     * 获取登录用户名
     * @return
     */
    String getLoginName();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * 是否禁用
     * @return
     */
    String getDisabled();
}
