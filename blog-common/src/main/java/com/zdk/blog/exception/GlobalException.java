package com.zdk.blog.exception;

import com.zdk.blog.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

/**
 * @author zdk
 */
public class GlobalException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(GlobalException.class);
    protected Integer errorCode;
    protected String[] errorMessageArguments;
    protected ApiResponse apiResponse;

    protected GlobalException() {
        this("");
    }

    public GlobalException(String message) {
        super(message);
        this.errorCode = 201;
        this.errorMessageArguments = new String[0];
    }

    public GlobalException(String message, Throwable cause) {
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

    public static GlobalException withErrorCode(Integer errorCode) {
        GlobalException globalException = new GlobalException();
        globalException.errorCode = errorCode;
        return globalException;
    }
    public static GlobalException withErrorMessage(String... errorMessage) {
        GlobalException globalException = new GlobalException();
        globalException.errorMessageArguments = errorMessage;
        return globalException;
    }

    public static GlobalException fromAPIResponse(ApiResponse apiResponse) {
        GlobalException globalException = new GlobalException();
        if(apiResponse == null) {
            apiResponse = ApiResponse.fail("NULL");
        }

        globalException.apiResponse = apiResponse;
        return globalException;
    }

    public GlobalException withErrorMessageArguments(String... errorMessageArguments) {
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
