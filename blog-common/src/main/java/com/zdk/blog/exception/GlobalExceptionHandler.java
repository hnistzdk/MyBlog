package com.zdk.blog.exception;

import com.zdk.blog.utils.BaseResponse;
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

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({BusinessException.class})
    public BaseResponse handleBusinessException(BusinessException e) {
        BaseResponse<String> baseResponse = new BaseResponse();
        this.logger.error("业务异常", e);
        baseResponse.setData("");
        baseResponse.error(e.getMessage());
        if (e.getErrorCode() != null) {
            baseResponse.setStatus(e.getErrorCode());
        }

        return baseResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public BaseResponse handleValidationException(Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        this.logger.error("参数校验错误", e);
        baseResponse.error(ErrorType.BAD_REQUEST, "参数校验错误");
        return baseResponse;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BaseResponse baseResponse = new BaseResponse();
        this.logger.error(e.getMessage(), e);
        StringBuffer errorMsg = new StringBuffer();
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            for (ObjectError objectError : allErrors) {
                if (objectError instanceof FieldError) {
                    FieldError fieldError = (FieldError) objectError;
                    errorMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage()).append(";");
                } else {
                    errorMsg.append(objectError.getDefaultMessage()).append(";");
                }
            }

            baseResponse.error(errorMsg.toString());
        } else {
            baseResponse.error(e.getMessage());
        }

        return baseResponse;
    }

    @ExceptionHandler({NoHandlerFoundException.class})
    public BaseResponse handlerNoFoundException(NoHandlerFoundException e) {
        this.logger.error(e.getMessage(), e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.error("404", "路径不存在，请检查路径是否正确");
        return baseResponse;
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public BaseResponse handleDuplicateKeyException(DuplicateKeyException e) {
        this.logger.error(e.getMessage(), e);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.error("数据库中已存在该记录");
        return baseResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public BaseResponse handleMissingServletRequestParameterException(Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        this.logger.error("参数解析失败", e);
        baseResponse.error(ErrorType.BAD_REQUEST, "参数解析失败");
        return baseResponse;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public BaseResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        BaseResponse baseResponse = new BaseResponse();
        this.logger.error("不支持当前请求方法", e);
        baseResponse.error(ErrorType.METHOD_NOT_ALLOWED, "不支持当前请求方法");
        return baseResponse;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public BaseResponse handleException(Exception e) {
        BaseResponse baseResponse = new BaseResponse();
        this.logger.error("系统异常", e);
        baseResponse.error(ErrorType.SYSTEM_ERROR, "系统异常");
        return baseResponse;
    }

}
