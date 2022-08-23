package com.zdk.blog.response;

import java.io.Serializable;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:42
 */
public class AbstractResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String code = "0";
    private String msg = "成功";

    public AbstractResponse error() {
        this.setCode("1");
        this.setMsg("系统异常");
        return this;
    }

    public AbstractResponse error(String msg) {
        this.setCode("1");
        this.setMsg(msg);
        return this;
    }

    public AbstractResponse error(String code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public AbstractResponse setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public AbstractResponse setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
