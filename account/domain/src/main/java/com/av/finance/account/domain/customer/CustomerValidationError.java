package com.av.finance.account.domain.customer;

import com.av.finance.account.common.BusinessException;

class CustomerValidationError extends BusinessException {

    CustomerValidationError(String message) {
        super(message);
    }
}
