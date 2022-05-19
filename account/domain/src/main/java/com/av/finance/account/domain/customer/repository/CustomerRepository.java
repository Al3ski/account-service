package com.av.finance.account.domain.customer.repository;

import com.av.finance.account.domain.customer.Customer;

import java.util.UUID;

public interface CustomerRepository {

    Customer retrieve(UUID customerId);

    void save(Customer customer);
}
