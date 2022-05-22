package com.av.finance.account.infrastructure.persistance.repository;

import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import com.av.finance.account.infrastructure.persistance.dao.CustomerDao;
import com.av.finance.account.infrastructure.persistance.entity.CustomerEntity;
import com.av.finance.account.infrastructure.persistance.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerDao customerDao;
    private final CustomerMapper mapper;

    @Override
    public Customer retrieve(UUID customerId) {
        final CustomerEntity entity = customerDao.findById(customerId).orElse(null);
        final Customer customer = mapper.toCustomer(entity);
        log.info("Found customer: {} for id: {}", customer, customerId);
        return customer;
    }

    @Override
    public void save(Customer customer) {
        customerDao.save(mapper.toEntity(customer));
        log.info("Customer: {} successfully created", customer.getCustomerId());
    }
}