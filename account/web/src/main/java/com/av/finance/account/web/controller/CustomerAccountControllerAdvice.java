package com.av.finance.account.web.controller;

import com.av.finance.account.common.BusinessException;
import com.av.finance.account.common.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Slf4j
@RestControllerAdvice
public class CustomerAccountControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessExceptions(BusinessException businessException, WebRequest request) {
        log.error("Application BusinessException happened", businessException);
        return new ErrorMessage(
                businessException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                "Check request input and see logs for details"
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleUnexpectedExceptions(Exception exception, WebRequest request) {
        log.error("Unexpected exception raised during application run", exception);
        return new ErrorMessage(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Something unexpected happened :("
        );
    }
}
