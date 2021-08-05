package com.zdk.MyBlog.utils;

/**
 * @author zdk
 * @date 2021/7/20 18:40
 */
public class ApiResponse<T>{

    private static final Integer CODE_SUCCESS = 200;

    private static final Integer CODE_FAIL = 201;

    private Integer code;
    private T data;
    private String msg;

    public ApiResponse(){
    }

    public ApiResponse(Integer code){
        this.code = code;
    }

    public ApiResponse(Integer code, T data){
        this.code = code;
        this.data = data;
    }

    public ApiResponse(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static <T> ApiResponse <T> success(){
        return new ApiResponse<>(CODE_SUCCESS);
    }

    public static <T> ApiResponse <T> success(T data){
        return new ApiResponse<>(CODE_SUCCESS, data);
    }

    public static <T> ApiResponse <T> successMsg(T msg){
        return new ApiResponse<>(CODE_SUCCESS, msg);
    }


    public static <T> ApiResponse <T> fail(String msg){
        return new ApiResponse<>(CODE_FAIL, msg);
    }

    public static <T> ApiResponse <T> widthCode(Integer errorCode) {
        return new ApiResponse<>(errorCode);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
