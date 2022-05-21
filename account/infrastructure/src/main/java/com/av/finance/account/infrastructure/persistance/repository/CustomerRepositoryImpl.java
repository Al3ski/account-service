package com.av.finance.account.infrastructure.persistance.repository;

import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    @Override
    public Customer retrieve(UUID customerId) {
        log.info("Found customer with id: {}", customerId);
        return Customer.builder()
                .customerId(customerId)
                .name("stab_name")
                .surname("stab_surname")
                .build();
    }

    @Override
    public void save(Customer customer) {
        log.info("Customer: {} successfully created", customer.getCustomerId());
    }
}
