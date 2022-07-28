package com.zdk.blog.utils;

import java.io.Serializable;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:42
 */
public class AbstractResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private String status = "0";
    private String msg = "成功";

    public void error() {
        this.setStatus("1");
        this.setMsg("系统异常");
    }

    public void error(String msg) {
        this.setStatus("1");
        this.setMsg(msg);
    }

    public void error(String status, String msg) {
        this.setStatus(status);
        this.setMsg(msg);
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
