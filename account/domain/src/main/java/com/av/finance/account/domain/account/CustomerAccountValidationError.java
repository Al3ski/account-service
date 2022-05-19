package com.av.finance.account.domain.account;

import com.av.finance.account.common.BusinessException;

class CustomerAccountValidationError extends RuntimeException implements BusinessException {

    CustomerAccountValidationError(String message) {
        super(message);
    }

    CustomerAccountValidationError(String message, Throwable cause) {
        super(message, cause);
    }
}
