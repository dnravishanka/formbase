package com.example.staticcheck.adviser;

import com.example.staticcheck.handler.AppException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppAdviser {
    @ExceptionHandler({Exception.class})
    public String handleMyException(Exception exception) {
        return exception.getMessage();
    }

    @ExceptionHandler({AppException.class})
    public String handlerException2(AppException appException) {
        return appException.getError();
    }
}
