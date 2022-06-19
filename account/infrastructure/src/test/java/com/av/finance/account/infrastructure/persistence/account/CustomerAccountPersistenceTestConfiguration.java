package com.av.finance.account.infrastructure.persistence.account;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.av.finance.account.infrastructure.persistence.account")
public class CustomerAccountPersistenceTestConfiguration {

    @Bean
    public CustomerAccountDao customerAccountDao() {
        return Mockito.mock(CustomerAccountDao.class);
    }
}
