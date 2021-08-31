package com.zdk.MyBlog.exception;

import com.zdk.MyBlog.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author zdk
 */
public class MyGlobalException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(MyGlobalException.class);
    protected Integer errorCode;
    protected String[] errorMessageArguments;
    protected ApiResponse apiResponse;

    protected MyGlobalException() {
        this("");
    }

    public MyGlobalException(String message) {
        super(message);
        this.errorCode = 201;
        this.errorMessageArguments = new String[0];
    }

    public MyGlobalException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = 201;
        this.errorMessageArguments = new String[0];
    }

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String[] getErrorMessageArguments() {
        return this.errorMessageArguments;
    }

    public void setErrorMessageArguments(String[] errorMessageArguments) {
        this.errorMessageArguments = errorMessageArguments;
    }

    public static MyGlobalException withErrorCode(Integer errorCode) {
        MyGlobalException myGlobalException = new MyGlobalException();
        myGlobalException.errorCode = errorCode;
        return myGlobalException;
    }
    public static MyGlobalException withErrorMessage(String... errorMessage) {
        MyGlobalException myGlobalException = new MyGlobalException();
        myGlobalException.errorMessageArguments = errorMessage;
        return myGlobalException;
    }

    public static MyGlobalException fromAPIResponse(ApiResponse apiResponse) {
        MyGlobalException myGlobalException = new MyGlobalException();
        if(apiResponse == null) {
            apiResponse = ApiResponse.fail("NULL");
        }

        myGlobalException.apiResponse = apiResponse;
        return myGlobalException;
    }

    public MyGlobalException withErrorMessageArguments(String... errorMessageArguments) {
        if(errorMessageArguments != null) {
            this.errorMessageArguments = errorMessageArguments;
        }

        return this;
    }
    public ApiResponse response() {
        if (this.apiResponse == null) {
            this.apiResponse = ApiResponse.failWidthDiyCode(this.getErrorCode());
            if (this.getErrorMessageArguments() != null && this.getErrorMessageArguments().length > 0) {
                try {
                    this.apiResponse.setMsg(MessageFormat.format(this.apiResponse.getMsg(), this.getErrorMessageArguments()));
                } catch (Exception var2) {
                    logger.error(var2.getMessage());
                }
            }

        }
        return this.apiResponse;
    }

}
