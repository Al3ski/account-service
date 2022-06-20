package com.av.finance.account.infrastructure.persistence.customer;

import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@Import(CustomerPersistenceTestConfiguration.class)
class CustomerRepositoryImplTest {

    @Autowired
    private CustomerFactory customerFactory;

    @Autowired
    private CustomerRepository customerRepository;

    @SpyBean
    private CustomerMapper customerMapper;

    @MockBean
    private CustomerDao customerDao;

    @Test
    void retrieveCustomerById_success() {
        final CustomerEntity customerEntity = customerFactory.createCustomerEntity();

        Mockito.when(customerDao.findById(customerEntity.getCustomerId())).thenReturn(Optional.of(customerEntity));

        final Customer customer = customerRepository.retrieve(customerEntity.getCustomerId());

        assertEquals(customer.getCustomerId(), customerEntity.getCustomerId());

        Mockito.verify(customerDao, times(1)).findById(customerEntity.getCustomerId());
        Mockito.verify(customerMapper, times(1)).toCustomer(customerEntity);
    }

    @Test
    void findAllCustomers_success() {
        final List<CustomerEntity> customerEntities = customerFactory.createCustomerEntities(10);

        Mockito.when(customerDao.findAll()).thenReturn(customerEntities);

        final List<Customer> customers = customerRepository.findAll();

        IntStream.range(0, customerEntities.size())
                .forEach(index -> {
                    assertEquals(customerEntities.get(index).getCustomerId(), customers.get(index).getCustomerId());
                });

        Mockito.verify(customerDao, times(1)).findAll();
        Mockito.verify(customerMapper, times(1)).toCustomers(any(List.class));
    }

    @Test
    void saveCustomer_success() {
        final Customer customer = customerFactory.createCustomer();

        customerRepository.save(customer);

        Mockito.verify(customerDao, times(1)).save(any(CustomerEntity.class));
        Mockito.verify(customerMapper, times(1)).toEntity(customer);
    }
}