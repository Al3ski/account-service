package com.av.finance.account.domain.account;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

public class CustomerAccountTest {

    @Test
    public void openCustomerAccount_success() {
        final UUID customerId = UUID.randomUUID();
        final CustomerAccount account = CustomerAccount.open(customerId, CustomerAccountType.CURRENT, BigDecimal.ZERO);

        Assert.assertNotNull(account);
        Assert.assertEquals(customerId, account.getCustomerId());
        Assert.assertEquals(CustomerAccountType.CURRENT, account.getAccountType());
        Assert.assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test(expected = CustomerAccountValidationError.class)
    public void openCustomerAccount_fail() {
        CustomerAccount.open(null, CustomerAccountType.CURRENT, BigDecimal.ZERO);
    }

    @Test
    public void buildCustomerAccount_success() {
        final UUID accountId = UUID.randomUUID();
        final UUID customerId = UUID.randomUUID();
        final CustomerAccount account = CustomerAccount.builder()
                .accountId(accountId)
                .customerId(customerId)
                .accountType(CustomerAccountType.CURRENT)
                .balance(BigDecimal.ZERO)
                .build();

        Assert.assertNotNull(account);
        Assert.assertEquals(accountId, account.getAccountId());
        Assert.assertEquals(customerId, account.getCustomerId());
        Assert.assertEquals(CustomerAccountType.CURRENT, account.getAccountType());
        Assert.assertEquals(BigDecimal.ZERO, account.getBalance());
    }

    @Test(expected = CustomerAccountValidationError.class)
    public void buildCustomerAccount_fail() {
        CustomerAccount.builder()
                .customerId(UUID.randomUUID())
                .accountType(CustomerAccountType.CURRENT)
                .balance(BigDecimal.ZERO)
                .build();
    }

    @Test
    public void openCustomerAccount_withNegativeBalance_fail() {
        final CustomerAccountValidationError validationError = Assert.assertThrows(
                CustomerAccountValidationError.class,
                () -> CustomerAccount.open(UUID.randomUUID(), CustomerAccountType.CURRENT, BigDecimal.valueOf(-1L))
        );

        Assert.assertEquals("Customer balance must be >= 0", validationError.getMessage());
    }
}