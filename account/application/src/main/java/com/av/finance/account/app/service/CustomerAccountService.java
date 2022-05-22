package com.av.finance.account.app.service;

import com.av.finance.account.app.dto.AccountDetails;
import com.av.finance.account.domain.account.CustomerAccountType;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface CustomerAccountService {

    List<AccountDetails> getCustomerAccountsDetails(UUID customerId);

    UUID openAccount(UUID customerId, CustomerAccountType accountType, BigDecimal initialCredit);
}
