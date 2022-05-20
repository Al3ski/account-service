package com.av.finance.account.app.service;

import com.av.finance.account.common.BusinessException;

public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
