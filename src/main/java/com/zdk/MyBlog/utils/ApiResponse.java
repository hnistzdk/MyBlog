package com.zdk.MyBlog.utils;

/**
 * @author zdk
 * @date 2021/7/20 18:40
 */
public class ApiResponse<T>{

    private static final Integer CODE_SUCCESS = 200;
    private static final Integer CODE_FAIL = 201;
    private static final String MSG_SUCCESS = "成功";
    private static final String MSG_FAIL = "失败";

    private Integer code;
    private T data;
    private String msg;

    public ApiResponse(){
        this.code=CODE_SUCCESS;
        this.msg=MSG_SUCCESS;
    }

    public ApiResponse(Integer code){
        this.code = code;
        this.msg=MSG_SUCCESS;
    }

    public ApiResponse(Integer code, T data){
        this.code = code;
        this.data = data;
        this.msg=MSG_SUCCESS;
    }

    public ApiResponse(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public ApiResponse(T data,String msg){
        this.code=CODE_SUCCESS;
        this.msg=msg;
        this.data=data;
    }

    public ApiResponse(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> ApiResponse <T> success(){
        return new ApiResponse<>();
    }
    public static <T> ApiResponse <T> result(Boolean success){
        return success ? new ApiResponse<>() : fail(MSG_FAIL);
    }
    public static <T> ApiResponse <T> success(T data){
        return new ApiResponse<>(CODE_SUCCESS, data,MSG_SUCCESS);
    }
    public static <T> ApiResponse <T> success(String msg){
        return new ApiResponse<>(CODE_SUCCESS, msg);
    }
    public static <T> ApiResponse <T> success(T data,String msg){
        return new ApiResponse<>(data,msg);
    }
    public static <T> ApiResponse <T> fail(){
        return new ApiResponse<>(CODE_FAIL,MSG_FAIL);
    }
    public static <T> ApiResponse <T> fail(String msg){
        return new ApiResponse<>(CODE_FAIL, msg);
    }
    public static <T> ApiResponse <T> failWidthDiyCode(Integer errorCode) {
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
