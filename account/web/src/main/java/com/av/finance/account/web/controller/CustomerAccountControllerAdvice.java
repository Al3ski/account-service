package com.av.finance.account.web.controller;

import com.av.finance.account.common.BusinessException;
import com.av.finance.account.common.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class CustomerAccountControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessExceptions(BusinessException businessException) {
        log.error("Application BusinessException happened", businessException);
        return new ErrorMessage(
                businessException.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                "Check request input and see logs for details"
        );
    }

    @ExceptionHandler({
            HttpMessageConversionException.class, MethodArgumentNotValidException.class,
            ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class,
            HttpMediaTypeNotSupportedException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleWrongInputMessageExceptions(Exception exception) {
        log.error("Incorrect request data format", exception);
        return new ErrorMessage(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.toString(),
                "Check request input and see logs for details"
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleUnexpectedExceptions(Exception exception) {
        log.error("Unexpected exception raised during application run", exception);
        return new ErrorMessage(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                "Something unexpected happened :("
        );
    }
}
