package com.av.finance.account.domain.account;

import com.av.finance.account.common.BusinessException;

class CustomerAccountValidationError extends BusinessException {

    CustomerAccountValidationError(String message) {
        super(message);
    }
}
