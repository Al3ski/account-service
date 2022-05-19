package com.av.finance.account.domain.account;

import java.math.BigDecimal;
import java.util.UUID;

class CustomerAccountBuilder {

    UUID accountId;
    UUID customerId;
    CustomerAccountType accountType;
    BigDecimal balance;

    public CustomerAccountBuilder accountId(java.util.UUID accountId) {
        this.accountId = accountId;
        return this;
    }

    public CustomerAccountBuilder customerId(UUID customerId) {
        this.customerId = customerId;
        return this;
    }

    public CustomerAccountBuilder accountType(CustomerAccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    public CustomerAccountBuilder balance(BigDecimal balance) {
        this.balance = balance;
        return this;
    }

    public CustomerAccount build() {
        return new CustomerAccount(this);
    }
}
