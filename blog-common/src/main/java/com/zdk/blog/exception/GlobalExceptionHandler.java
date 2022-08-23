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

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zhangdikai
 * @date 2022-07-28 14:34
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理业务异常
     * @param e
     * @return
     */
    @ExceptionHandler({BusinessException.class})
    public ApiResponse businessExceptionHandler(BusinessException e) {
        ApiResponse<String> response = ApiResponse.fail();
        logger.error(e.getMessage(), e);
        response.error(e.getMessage());
        if (e.getErrorCode() != null) {
            response.setCode(e.getErrorCode());
        }

        return response;
    }

    /**
     * 处理 form data方式调用接口校验失败抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BindException.class})
    public ApiResponse validationExceptionHandler(Exception e) {
        ApiResponse response = ApiResponse.fail();
        logger.error(e.getMessage(), e);
        response.error(ErrorType.BAD_REQUEST, e.getMessage());
        return response;
    }

    /**
     * 处理json请求体调用参数异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ApiResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        ApiResponse response = ApiResponse.fail();
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

    /**
     * 处理单个参数校验失败抛出的异常
     * @param e
     * @return
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ApiResponse constraintViolationExceptionHandler(ConstraintViolationException e) {
        ApiResponse response = ApiResponse.fail();
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        List<String> collect = constraintViolations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        response.error(ErrorType.BAD_REQUEST,collect.toString());
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoHandlerFoundException.class})
    public ApiResponse noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        logger.error(e.getMessage(), e);
        ApiResponse response = ApiResponse.fail();
        response.error("404", "路径不存在，请检查路径是否正确");
        return response;
    }

    @ExceptionHandler({DuplicateKeyException.class})
    public ApiResponse duplicateKeyExceptionHandler(DuplicateKeyException e) {
        logger.error(e.getMessage(), e);
        ApiResponse response = ApiResponse.fail();
        response.error("数据库中已存在该记录");
        return response;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public ApiResponse missingServletRequestParameterExceptionHandler(Exception e) {
        ApiResponse response = ApiResponse.fail();
        logger.error("参数解析失败", e);
        response.error(ErrorType.BAD_REQUEST, "参数解析失败");
        return response;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ApiResponse httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e) {
        ApiResponse response = ApiResponse.fail();
        logger.error("不支持当前请求方法", e);
        response.error(ErrorType.METHOD_NOT_ALLOWED, "不支持当前请求方法");
        return response;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public ApiResponse simpleExceptionHandler(Exception e) {
        ApiResponse response = ApiResponse.fail();
        logger.error("系统异常", e);
        response.error(ErrorType.SYSTEM_ERROR, "系统异常");
        return response;
    }

}
