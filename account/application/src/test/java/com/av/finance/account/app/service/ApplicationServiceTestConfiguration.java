package com.av.finance.account.app.service;

import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "com.av.finance.account.app.service")
public class ApplicationServiceTestConfiguration {

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerAccountRepository customerAccountRepository;
}
