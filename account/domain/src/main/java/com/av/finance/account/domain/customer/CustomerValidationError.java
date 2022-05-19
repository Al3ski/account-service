package com.av.finance.account.domain.customer;

import com.av.finance.account.common.BusinessException;

class CustomerValidationError extends RuntimeException implements BusinessException {

    CustomerValidationError(String message) {
        super(message);
    }

    CustomerValidationError(String message, Throwable cause) {
        super(message, cause);
    }
}
