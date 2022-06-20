package com.av.finance.account.infrastructure.persistence.customer;

import com.av.finance.account.domain.customer.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@Import(CustomerPersistenceTestConfiguration.class)
class CustomerMapperTest {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerFactory customerFactory;

    @Test
    void mapEntityToCustomer_success() {
        final CustomerEntity customerEntity = customerFactory.createCustomerEntity();

        final Customer customer = customerMapper.toCustomer(customerEntity);

        assertEquals(customerEntity.getCustomerId(), customer.getCustomerId());
        assertEquals(customerEntity.getName(), customer.getName());
        assertEquals(customerEntity.getSurname(), customer.getSurname());
    }

    @Test
    void mapEntityToCustomer_ifNullEntity_success() {
        final Customer customer = customerMapper.toCustomer(null);

        assertNull(customer);
    }

    @Test
    void mapEntitiesToCustomers_success() {
        final List<CustomerEntity> customerEntities = customerFactory.createCustomerEntities();

        final List<Customer> customers = customerMapper.toCustomers(customerEntities);

        IntStream.range(0, customerEntities.size())
                .forEach(index -> {
                    assertEquals(customerEntities.get(index).getCustomerId(), customers.get(index).getCustomerId());
                    assertEquals(customerEntities.get(index).getName(), customers.get(index).getName());
                    assertEquals(customerEntities.get(index).getSurname(), customers.get(index).getSurname());
                });
    }

    @Test
    void mapEntitiesToCustomers_ifEmptyEntities_success() {
        final List<CustomerEntity> customerEntities = Collections.emptyList();

        final List<Customer> customers = customerMapper.toCustomers(customerEntities);

        assertNotNull(customers);
        assertEquals(0, customers.size());
    }

    @Test
    void mapEntitiesToCustomers_ifNullEntities_success() {
        final List<Customer> customers = customerMapper.toCustomers(null);

        assertNull(customers);
    }

    @Test
    void mapCustomerToEntity_success() {
        final Customer customer = customerFactory.createCustomer();

        final CustomerEntity customerEntity = customerMapper.toEntity(customer);

        assertEquals(customer.getCustomerId(), customerEntity.getCustomerId());
        assertEquals(customer.getName(), customerEntity.getName());
        assertEquals(customer.getSurname(), customerEntity.getSurname());
    }

    @Test
    void mapCustomerToEntity_ifNullCustomer_success() {
        final CustomerEntity customerEntity = customerMapper.toEntity(null);

        assertNull(customerEntity);
    }

    @Test
    void mapCustomersToEntities_success() {
        final List<Customer> customers = customerFactory.createCustomers();

        final List<CustomerEntity> customerEntities = customerMapper.toEntities(customers);

        IntStream.range(0, customers.size())
                .forEach(index -> {
                    assertEquals(customers.get(index).getCustomerId(), customerEntities.get(index).getCustomerId());
                    assertEquals(customers.get(index).getName(), customerEntities.get(index).getName());
                    assertEquals(customers.get(index).getSurname(), customerEntities.get(index).getSurname());
                });
    }

    @Test
    void mapCustomersToEntities_ifEmptyCustomers_success() {
        final List<Customer> customers = Collections.emptyList();

        final List<CustomerEntity> customerEntities = customerMapper.toEntities(customers);

        assertNotNull(customerEntities);
        assertEquals(0, customerEntities.size());
    }
}