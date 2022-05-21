package com.av.finance.account.infrastructure.persistance.repository;

import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.CustomerAccountType;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class CustomerAccountRepositoryImpl implements CustomerAccountRepository {

    @Override
    public CustomerAccount retrieve(UUID accountId) {
        log.info("Found customer account with id: {}", accountId);
        return CustomerAccount.builder()
                .customerId(UUID.randomUUID())
                .accountId(accountId)
                .accountType(CustomerAccountType.CURRENT)
                .balance(BigDecimal.ZERO)
                .build();
    }

    @Override
    public List<CustomerAccount> retrieveByCustomer(UUID customerId) {
        log.info("Found customer accounts for customer: {}", customerId);
        return new ArrayList<>();
    }

    @Override
    public void save(CustomerAccount customerAccount) {
        log.info("Customer account: {} successfully created", customerAccount.getAccountId());
    }
}
