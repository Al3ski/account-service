package com.av.finance.account.domain.customer.repository;

import com.av.finance.account.domain.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository {

    Customer retrieve(UUID customerId);

    List<Customer> findAll();

    void save(Customer customer);
}
