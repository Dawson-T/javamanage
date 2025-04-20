package com.cqgs.plus.config;

import com.cqgs.plus.util.HttpResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler
    public HttpResult exception(Exception ex){
        System.out.println("出现了异常：" +ex.getMessage());
        return HttpResult.errorResult(ex.getMessage());
    }
}
