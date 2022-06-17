package com.av.finance.account.web.config;

import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.web.controller.CustomerAccountController;
import com.av.finance.account.web.controller.CustomerAccountControllerAdvice;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerAccountWebTestConfiguration {

    @Bean
    public CustomerAccountService customerAccountService() {
        return Mockito.mock(CustomerAccountService.class);
    }

    @Bean
    public CustomerAccountController customerAccountController(CustomerAccountService customerAccountService) {
        return new CustomerAccountController(customerAccountService);
    }

    @Bean
    public CustomerAccountControllerAdvice customerAccountControllerAdvice() {
        return Mockito.spy(CustomerAccountControllerAdvice.class);
    }
}
