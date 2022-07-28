package com.zdk.blog.common.exception;

import java.io.Serializable;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:41
 */
public class Error implements Serializable {
    private static final long serialVersionUID = 3L;
    private String clazz;
    private String method;
    private int lineNumber;
    private String code;
    private ErrorType type;
    private String message;

    public Error() {
    }

    public Error(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public Error(ErrorType type, String message) {
        this.type = type;
        this.code = type.toString();
        this.message = message;
    }

    public String getClazz() {
        return this.clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorType getType() {
        return this.type;
    }

    public void setType(ErrorType type) {
        this.type = type;
    }
}
