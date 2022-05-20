package com.av.finance.account.domain.transaction;

import com.av.finance.account.common.BusinessException;

class TransactionValidationError extends BusinessException {

    TransactionValidationError(String message) {
        super(message);
    }
}
