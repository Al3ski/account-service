package com.av.finance.account.domain.account.repository;

import com.av.finance.account.domain.account.CustomerAccount;

import java.util.List;
import java.util.UUID;

public interface CustomerAccountRepository {

    CustomerAccount retrieve(UUID accountId);

    List<CustomerAccount> retrieveByCustomer(UUID customerId);

    List<CustomerAccount> retrieveByCustomers(Iterable<UUID> customerIds);

    void save(CustomerAccount customerAccount);
}
