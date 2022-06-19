package com.av.finance.account.infrastructure.persistence.customer;

import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
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
        log.debug("Found customer: {} for id: {}", customer, customerId);
        return customer;
    }

    @Override
    public List<Customer> findAll() {
        final Iterable<CustomerEntity> allEntities = customerDao.findAll();
        final List<Customer> customers = mapper.toCustomers(allEntities);
        log.debug("Found customers in the system: {}", customers);
        return customers;
    }

    @Override
    public void save(Customer customer) {
        customerDao.save(mapper.toEntity(customer));
        log.debug("Customer: {} successfully created", customer.getCustomerId());
    }
}
