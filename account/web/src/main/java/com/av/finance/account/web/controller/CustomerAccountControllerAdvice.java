package com.av.finance.account.web.controller;

import com.av.finance.account.common.BusinessException;
import com.av.finance.account.web.response.error.ErrorMessage;
import com.av.finance.account.web.response.error.ErrorMessageFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.util.MimeType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomerAccountControllerAdvice {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleBusinessExceptions(BusinessException exception) {
        log.error("Application BusinessException happened", exception);
        return ErrorMessageFactory.BUSINESS_ERROR.fromThrowable(exception);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleNoHandlerFoundException(NoHandlerFoundException exception) {
        log.error("No handler found for path: {}", exception.getRequestURL(), exception);
        return ErrorMessageFactory.NOT_FOUND.fromThrowable(exception);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ErrorMessage handleMethodNotAllowedException(HttpRequestMethodNotSupportedException exception,
                                                        HttpServletResponse response) {
        log.error("{} method not allowed. Allowed methods: {}", exception.getMethod(),
                exception.getSupportedHttpMethods(), exception);
        final Set<HttpMethod> supportedMethods = exception.getSupportedHttpMethods();
        if (supportedMethods != null) {
            response.addHeader("Allowed", supportedMethods.stream()
                    .map(HttpMethod::toString)
                    .collect(Collectors.joining(",")));
        }
        return ErrorMessageFactory.NOT_ALLOWED_METHOD.fromThrowable(exception);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    public ErrorMessage handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException exception,
                                                            HttpServletResponse response) {
        log.error("{} media type not supported. Supported media types: {}", exception.getContentType(),
                exception.getSupportedMediaTypes(), exception);
        response.addHeader("Accept", exception.getSupportedMediaTypes().stream()
                .map(MimeType::toString)
                .collect(Collectors.joining(",")));
        return ErrorMessageFactory.UNSUPPORTED_MEDIA_TYPE.fromThrowable(exception);
    }

    @ExceptionHandler({
            HttpMessageConversionException.class, MethodArgumentNotValidException.class,
            ServletRequestBindingException.class, MethodArgumentTypeMismatchException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleWrongInputExceptions(Exception exception) {
        log.error("Incorrect request data format", exception);
        return ErrorMessageFactory.BAD_REQUEST.fromThrowable(exception);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage handleUnexpectedExceptions(Exception exception) {
        log.error("Unexpected exception raised during application run", exception);
        return ErrorMessageFactory.INTERNAL_ERROR.fromThrowable(exception);
    }
}
