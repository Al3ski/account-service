package com.av.finance.account.infrastructure.persistence.account;

import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.CustomerAccountType;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
class CustomerAccountFactory {

    public CustomerAccount createCustomerAccount() {
        return CustomerAccount.builder()
                .customerId(UUID.randomUUID())
                .accountId(UUID.randomUUID())
                .accountType(CustomerAccountType.CURRENT)
                .balance(BigDecimal.ZERO)
                .build();
    }

    public CustomerAccountEntity createCustomerAccountEntity() {
        final CustomerAccountEntity customerAccountEntity = new CustomerAccountEntity();
        customerAccountEntity.setCustomerId(UUID.randomUUID());
        customerAccountEntity.setAccountId(UUID.randomUUID());
        customerAccountEntity.setAccountType("CURRENT");
        customerAccountEntity.setBalance(BigDecimal.ZERO);
        return customerAccountEntity;
    }

    public List<CustomerAccount> createCustomerAccounts() {
        return createCustomerAccounts(false);
    }

    public List<CustomerAccount> createCustomerAccounts(boolean multipleCustomers) {
        return multipleCustomers ? Lists.newArrayList(createCustomerAccount(), createCustomerAccount())
                : Collections.singletonList(createCustomerAccount());
    }

    public List<CustomerAccountEntity> createCustomerAccountEntities() {
        return createCustomerAccountEntities(false);
    }

    public List<CustomerAccountEntity> createCustomerAccountEntities(boolean multipleCustomers) {
        return multipleCustomers ? Lists.newArrayList(createCustomerAccountEntity(), createCustomerAccountEntity())
                : Collections.singletonList(createCustomerAccountEntity());
    }
}
