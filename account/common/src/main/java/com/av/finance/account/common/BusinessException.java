package com.av.finance.account.common;

public class BusinessException extends RuntimeException {

    protected BusinessException() {
        super();
    }

    protected BusinessException(String message) {
        super(message);
    }
}
