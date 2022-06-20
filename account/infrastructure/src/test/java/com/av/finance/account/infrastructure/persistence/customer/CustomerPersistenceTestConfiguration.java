package com.av.finance.account.infrastructure.persistence.customer;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.av.finance.account.infrastructure.persistence.customer")
public class CustomerPersistenceTestConfiguration {

    @Bean
    public CustomerDao customerDao() {
        return Mockito.mock(CustomerDao.class);
    }
}
