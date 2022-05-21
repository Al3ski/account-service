package com.av.finance.account.app.service;

import com.av.finance.account.common.BusinessException;
import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import com.av.finance.account.domain.transaction.TransactionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static com.av.finance.account.domain.account.CustomerAccountType.CURRENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CustomerAccountServiceTest {

    @Autowired
    private CustomerAccountService customerAccountService;

    @MockBean
    private TransactionService transactionService;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private CustomerAccountRepository customerAccountRepository;

    @Test
    void openCurrentAccount_success() {
        final UUID customerId = UUID.randomUUID();

        final Customer customer = prepareCustomer(customerId);

        Mockito.when(transactionService.createTransaction(any(UUID.class), any(TransactionType.class),
                any(BigDecimal.class), any(String.class))).thenReturn(any(UUID.class));
        Mockito.when(customerRepository.retrieve(customerId)).thenReturn(customer);
        Mockito.doNothing().when(customerAccountRepository).save(any(CustomerAccount.class));

        final UUID accountId = customerAccountService.openAccount(customerId, CURRENT, BigDecimal.ZERO);

        Assertions.assertNotNull(accountId);

        Mockito.verify(customerRepository, times(1)).retrieve(customerId);
        Mockito.verify(customerAccountRepository, times(1)).save(any(CustomerAccount.class));
    }

    @Test
    void openCurrentAccount_withNegativeBalance_fail() {
        final UUID customerId = UUID.randomUUID();

        final Customer customer = prepareCustomer(customerId);

        Mockito.when(customerRepository.retrieve(customerId)).thenReturn(customer);

        final BusinessException exception = Assertions.assertThrows(
                BusinessException.class,
                () -> customerAccountService.openAccount(customerId, CURRENT, BigDecimal.valueOf(-1L))
        );

        Assertions.assertEquals("Customer balance must be >= 0", exception.getMessage());
    }

    private Customer prepareCustomer(UUID customerId) {
        return Customer.builder()
                .customerId(customerId)
                .name("username")
                .surname("surname")
                .build();
    }

    @TestConfiguration
    @ComponentScan(basePackages = "com.av.finance.account.app.service")
    static class CustomerAccountServiceConfiguration {
    }
}
