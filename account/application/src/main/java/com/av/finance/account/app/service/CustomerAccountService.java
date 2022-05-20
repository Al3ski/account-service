package com.av.finance.account.app.service;

import com.av.finance.account.domain.account.CustomerAccountType;

import java.math.BigDecimal;
import java.util.UUID;

public interface CustomerAccountService {

    UUID openAccount(UUID customerId, CustomerAccountType accountType, BigDecimal initialCredit);
}
