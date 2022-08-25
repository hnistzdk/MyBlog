package com.zdk.blog.security;

import java.util.Date;

/**
 * @Description
 * @Author zdk
 * @Date 2022/8/25 10:59
 */
public class Payload<T> {
    private String id;
    private T userInfo;
    private Date expiration;

    public String getId() {
        return this.id;
    }

    public T getUserInfo() {
        return this.userInfo;
    }

    public Date getExpiration() {
        return this.expiration;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserInfo(T userInfo) {
        this.userInfo = userInfo;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
