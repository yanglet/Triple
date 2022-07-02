package com.report.triple.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e){
        log.error("methodArgumentNotValidExceptionHandler ", e);
        ValidationErrorResult errorResult = new ValidationErrorResult("400", "Validation error!");
        errorResult.addValidationError(e.getFieldError().getField(), e.getFieldError().getDefaultMessage());
        return errorResult;
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler
    protected ErrorResult httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException e){
        log.error("httpRequestMethodNotSupportedExceptionHandler ", e);
        return new ErrorResult("405", "지원하지않는 Http Method 입니다. ");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    protected ErrorResult defaultExceptionHandler(Exception e){
        log.error("DefaultExceptionHandler", e);
        return new ErrorResult("500", "서버 오류!");
    }
}
