package com.av.finance.account.infrastructure.persistence.customer;

import com.av.finance.account.domain.customer.Customer;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class CustomerFactory {

    public Customer createCustomer() {
        return Customer.builder()
                .customerId(UUID.randomUUID())
                .name("Ivan:" + RandomUtils.nextInt(1, 1000))
                .surname("Ivanov:" + RandomUtils.nextInt(1, 1000))
                .build();
    }

    public CustomerEntity createCustomerEntity() {
        final CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setCustomerId(UUID.randomUUID());
        customerEntity.setName("Ivan:" + RandomUtils.nextInt(1, 1000));
        customerEntity.setSurname("Ivanov:" + RandomUtils.nextInt(1, 1000));
        return customerEntity;
    }

    public List<Customer> createCustomers() {
        return createCustomers(1);
    }

    public List<Customer> createCustomers(int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> createCustomer())
                .collect(Collectors.toList());
    }

    public List<CustomerEntity> createCustomerEntities() {
        return createCustomerEntities(1);
    }

    public List<CustomerEntity> createCustomerEntities(int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> createCustomerEntity())
                .collect(Collectors.toList());
    }
}
