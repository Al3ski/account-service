package com.av.finance.account.web.config;

import com.av.finance.account.app.service.CustomerAccountService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.av.finance.account.web")
public class CustomerAccountWebTestConfiguration {

    @Bean
    public CustomerAccountService customerAccountService() {
        return Mockito.mock(CustomerAccountService.class);
    }
}
