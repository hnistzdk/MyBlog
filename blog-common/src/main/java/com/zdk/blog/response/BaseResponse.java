package com.zdk.blog.response;

import com.zdk.blog.exception.Error;
import com.zdk.blog.exception.ErrorType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:43
 */
public class BaseResponse<T> extends AbstractResponse {
    private static final long serialVersionUID = 1L;
    private T data;
    private List<Error> errors;

    public BaseResponse error() {
        super.error();
        Error error = new Error("1", "系统异常");

        try {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception var3) {
        }

        this.getErrors().add(error);
        return this;
    }

    public BaseResponse error(String message) {
        super.error(message);
        Error error = new Error("1", message);

        try {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception var4) {
        }

        this.getErrors().add(error);
        return this;
    }

    public BaseResponse error(ErrorType type) {
        super.error(type.getCode(), type.getMsg());
        Error error = new Error(type.getCode(), type.getMsg());

        try {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception var4) {
        }

        this.getErrors().add(error);
        return this;
    }

    public BaseResponse error(String code, String message) {
        super.error(code, message);
        Error error = new Error(code, message);
        this.setStatus(code);
        this.setMsg(message);

        try {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception var5) {
        }

        this.getErrors().add(error);
        return this;
    }

    public BaseResponse error(ErrorType type, String message) {
        super.error(type.getCode(), message);
        Error error = new Error(type, message);

        try {
            StackTraceElement ste = (new Throwable()).getStackTrace()[1];
            error.setClazz(ste.getClassName());
            error.setMethod(ste.getMethodName());
            error.setLineNumber(ste.getLineNumber());
        } catch (Exception var5) {
        }

        this.getErrors().add(error);
        return this;
    }

    public BaseResponse addError(Error error) {
        this.getErrors().add(error);
        return this;
    }

    public BaseResponse addErrors(List<Error> errors) {
        Iterator var2 = errors.iterator();

        while(var2.hasNext()) {
            Error error = (Error)var2.next();
            this.addError(error);
        }
        return this;

    }

    public boolean hasError() {
        return this.errors != null && !this.errors.isEmpty();
    }

    public List<Error> getErrors() {
        if (this.errors == null) {
            this.errors = new ArrayList();
        }

        return this.errors;
    }

    public BaseResponse ok(T data) {
        this.setStatus("0");
        this.setMsg("成功");
        this.data = data;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public BaseResponse setData(T data) {
        this.data = data;
        return this;
    }
}
