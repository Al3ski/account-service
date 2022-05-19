package com.av.finance.account.domain.account;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

public class CustomerAccount {

    private UUID accountId;
    private UUID customerId;
    private CustomerAccountType accountType;
    private BigDecimal balance;

    private CustomerAccount(UUID accountId, UUID customerId, CustomerAccountType accountType, BigDecimal balance) {
        if (accountId == null) {
            throw new CustomerAccountValidationError("Customer account id can't be null");
        }
        if (customerId == null) {
            throw new CustomerAccountValidationError("Customer must be provided");
        }
        if (accountType == null) {
            throw new CustomerAccountValidationError("Customer account type required");
        }
        if (balance == null || balance.longValue() < 0) {
            throw new CustomerAccountValidationError("Customer balance must be >= 0");
        }
        this.accountId = accountId;
        this.customerId = customerId;
        this.accountType = accountType;
        this.balance = balance;
    }

    CustomerAccount(CustomerAccountBuilder builder) {
        this(builder.accountId, builder.customerId, builder.accountType, builder.balance);
    }

    public CustomerAccount open(UUID customerId, CustomerAccountType accountType, BigDecimal initialCredit) {
        return new CustomerAccount(UUID.randomUUID(), customerId, accountType, initialCredit);
    }

    public static CustomerAccountBuilder builder() {
        return new CustomerAccountBuilder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerAccount that = (CustomerAccount) o;
        return Objects.equals(accountId, that.accountId) &&
                accountType == that.accountType &&
                Objects.equals(customerId, that.customerId) &&
                Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, accountType, customerId, balance);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CustomerAccount.class.getSimpleName() + "[", "]")
                .add("accountId=" + accountId)
                .add("accountType=" + accountType)
                .add("customerId=" + customerId)
                .add("balance=" + balance)
                .toString();
    }
}
