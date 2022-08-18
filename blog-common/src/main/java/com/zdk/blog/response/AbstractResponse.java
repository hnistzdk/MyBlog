package com.zdk.blog.response;

import java.io.Serializable;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:42
 */
public class AbstractResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status = "0";
    private String msg = "成功";

    public AbstractResponse error() {
        this.setStatus("1");
        this.setMsg("系统异常");
        return this;
    }

    public AbstractResponse error(String msg) {
        this.setStatus("1");
        this.setMsg(msg);
        return this;
    }

    public AbstractResponse error(String status, String msg) {
        this.setStatus(status);
        this.setMsg(msg);
        return this;
    }

    public String getStatus() {
        return this.status;
    }

    public AbstractResponse setStatus(String status) {
        this.status = status;
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
