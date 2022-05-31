package com.av.finance.account.web.response.error;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

@RequiredArgsConstructor
public enum ErrorMessageFactory {
    BAD_REQUEST(HttpStatus.BAD_REQUEST),
    BUSINESS_ERROR(HttpStatus.UNPROCESSABLE_ENTITY),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Check request url. See logs for details"),
    NOT_ALLOWED_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "Check http request method. See logs for details"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "Check request media type. See logs for details"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something unexpected happened :(");

    private final HttpStatus httpStatus;

    private String details;

    private final Supplier<String> detailsSupplier = () -> StringUtils.isBlank(details)
            ? "Check request input data. See logs for details" : details;

    ErrorMessageFactory(HttpStatus status, String details) {
        this.httpStatus = status;
        this.details = details;
    }

    public ErrorMessage fromThrowable(Throwable exception) {
        return new ErrorMessage(
                httpStatus.value(), httpStatus.getReasonPhrase(),
                exception.getMessage(), detailsSupplier.get()
        );
    }
}
