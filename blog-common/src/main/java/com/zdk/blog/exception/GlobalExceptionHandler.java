package com.zdk.blog.exception;

import com.zdk.blog.response.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.List;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:34
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({BusinessException.class})
    public ApiResponse handleBusinessException(BusinessException e) {
        ApiResponse<String> response = new ApiResponse();
        logger.error("业务异常", e);
        response.setData("");
        response.error(e.getMessage());
        if (e.getErrorCode() != null) {
            response.setStatus(e.getErrorCode());
        }

        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ApiResponse handleValidationException(Exception e) {
        System.out.println("进入参数绑定异常");
        ApiResponse response = new ApiResponse();
        logger.error("参数校验错误", e);
        response.error(ErrorType.BAD_REQUEST, e.getMessage());
        return response;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ApiResponse response = new ApiResponse();
        logger.error(e.getMessage(), e);
        StringBuilder errorMsg = new StringBuilder();
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (!allErrors.isEmpty()) {
            for (ObjectError objectError : allErrors) {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
                } else {
                    errorMsg.append(objectError.getDefaultMessage()).append(";");
                }
            }

            response.error(errorMsg.toString());
        } else {
            response.error(e.getMessage());
        }

        return response;
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public ApiResponse handlerNoFoundException(NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        ApiResponse response = new ApiResponse();
        response.error("404", "路径不存在，请检查路径是否正确");
        return response;
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ApiResponse handleDuplicateKeyException(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        ApiResponse response = new ApiResponse();
        response.error("数据库中已存在该记录");
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ApiResponse handleMissingServletRequestParameterException(Exception e) {
        ApiResponse response = new ApiResponse();
        logger.error("参数解析失败", e);
        response.error(ErrorType.BAD_REQUEST, "参数解析失败");
        return response;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ApiResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        ApiResponse response = new ApiResponse();
        logger.error("不支持当前请求方法", e);
        response.error(ErrorType.METHOD_NOT_ALLOWED, "不支持当前请求方法");
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ApiResponse handleException(Exception e) {
        System.out.println("进入普通异常");
        ApiResponse response = new ApiResponse();
        logger.error("系统异常", e);
        response.error(ErrorType.SYSTEM_ERROR, "系统异常");
        return response;
    }

}
