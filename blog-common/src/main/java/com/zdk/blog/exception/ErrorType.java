package com.zdk.blog.exception;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:41
 */
@SuppressWarnings("all")
public enum ErrorType {
    OK("200", "OK"),
    HTTP_ERROR_100("100", "1XX错误"),
    HTTP_ERROR_300("300", "3XX错误"),
    HTTP_ERROR_400("400", "4XX错误"),
    HTTP_ERROR_500("500", "5XX错误"),
    BAD_REQUEST("407", "参数解析失败"),
    REQUEST_NOT_FOUND("404", "访问地址不存在！"),
    METHOD_NOT_ALLOWED("405", "不支持当前请求方法"),
    BUSINESS_ERROR("10000", "期待值为空找不到想要的对象"),
    SYSTEM_ERROR("10001", "系统运行时异常"),
    UNIQUENESS_ERROR("10002", "唯一性错误，出现了不允许重复的内容"),
    EXPECTATION_NULL("10003", "期待值为空找不到想要的对象"),
    INVALID_PARAMETER("10004", "期待值为空找不到想要的对象"),
    OTHER("99999", "期待值为空找不到想要的对象");

    private String code;
    private String msg;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public static ErrorType getByCode(String code) {
        ErrorType[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ErrorType e = var1[var3];
            if (e.getCode().equals(code)) {
                return e;
            }
        }

        return null;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ErrorType(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
