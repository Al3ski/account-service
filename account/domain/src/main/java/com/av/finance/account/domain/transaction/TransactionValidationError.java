package com.av.finance.account.domain.transaction;

import com.av.finance.account.common.BusinessException;

class TransactionValidationError extends RuntimeException implements BusinessException {

    TransactionValidationError(String message) {
        super(message);
    }

    TransactionValidationError(String message, Throwable cause) {
        super(message, cause);
    }
}
