package com.av.finance.account.app.service;

import com.av.finance.account.app.dto.AccountDetails;
import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.external.TransactionExternalService;
import com.av.finance.account.common.BusinessException;
import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.av.finance.account.domain.account.CustomerAccountType.CURRENT;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
class CustomerAccountServiceTest {

    @Autowired
    private CustomerAccountService customerAccountService;

    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private CustomerAccountRepository customerAccountRepository;
    @MockBean
    private TransactionExternalService transactionService;

    private Customer customer;

    @BeforeEach
    private void init() {
        customer = Customer.builder()
                .customerId(UUID.randomUUID())
                .name("username")
                .surname("surname")
                .build();
    }

    @AfterEach
    private void reset() {
        Mockito.reset(customerRepository, customerAccountRepository, transactionService);
    }

    @Nested
    class OpenCurrentAccountTest {

        @Test
        void openCurrentAccount_success() {
            Mockito.doNothing().when(transactionService).createTransaction(any(TxDetails.class));
            Mockito.when(customerRepository.retrieve(customer.getCustomerId())).thenReturn(customer);
            Mockito.doNothing().when(customerAccountRepository).save(any(CustomerAccount.class));

            final UUID accountId = customerAccountService.openAccount(customer.getCustomerId(), CURRENT, BigDecimal.ZERO);

            Assertions.assertNotNull(accountId);

            Mockito.verify(customerRepository, times(1)).retrieve(customer.getCustomerId());
            Mockito.verify(customerAccountRepository, times(1)).save(any(CustomerAccount.class));
            Mockito.verify(transactionService, times(0)).createTransaction(any(TxDetails.class));
        }

        @Test
        void openCurrentAccount_withNotExistingCustomer_fail() {
            Mockito.when(customerRepository.retrieve(any(UUID.class))).thenReturn(null);

            final CustomerNotFoundException notFoundException = Assertions.assertThrows(
                    CustomerNotFoundException.class,
                    () -> customerAccountService.openAccount(UUID.randomUUID(), CURRENT, BigDecimal.ZERO)
            );

            Assertions.assertEquals(notFoundException.getClass(), CustomerNotFoundException.class);
        }

        @Test
        void openCurrentAccount_withNegativeBalance_fail() {
            Mockito.when(customerRepository.retrieve(customer.getCustomerId())).thenReturn(customer);

            final BusinessException exception = Assertions.assertThrows(
                    BusinessException.class,
                    () -> customerAccountService.openAccount(customer.getCustomerId(), CURRENT, BigDecimal.valueOf(-1L))
            );

            Assertions.assertEquals("Customer balance must be >= 0", exception.getMessage());
        }
    }

    @Nested
    class GetCustomerAccountsDetailsTest {

        private List<CustomerAccount> customerAccounts;

        @BeforeEach
        private void init() {
            final List<UUID> customerIds = singletonList(customer.getCustomerId());
            final BigDecimal balance = BigDecimal.valueOf(Math.random())
                    .movePointRight(2)
                    .setScale(3, RoundingMode.HALF_UP);

            customerAccounts = customerIds.stream()
                    .filter(Objects::nonNull)
                    .map(customerId -> CustomerAccount.builder()
                            .customerId(customerId)
                            .accountId(UUID.randomUUID())
                            .accountType(CURRENT)
                            .balance(balance)
                            .build()
                    )
                    .collect(Collectors.toList());
        }

        @Test
        void getCustomerAccountsDetails_success() {
            Mockito.when(customerRepository.retrieve(customer.getCustomerId())).thenReturn(customer);
            Mockito.when(customerAccountRepository.retrieveByCustomers(any(Set.class))).thenReturn(customerAccounts);
            Mockito.when(transactionService.getTransactionsForAccounts(any(List.class))).thenReturn(emptyList());

            final List<AccountDetails> accountsDetails = customerAccountService.getCustomerAccountsDetails(customer.getCustomerId());

            Assertions.assertNotNull(accountsDetails);

            Mockito.verify(customerRepository, times(1)).retrieve(customer.getCustomerId());
            Mockito.verify(customerAccountRepository, times(1)).retrieveByCustomers(any(Set.class));
            Mockito.verify(transactionService, times(1)).getTransactionsForAccounts(any(List.class));
        }

        @Test
        void getAllCustomersAccountsDetails_success() {
            Mockito.when(customerRepository.findAll()).thenReturn(singletonList(customer));
            Mockito.when(customerAccountRepository.retrieveByCustomers(any(Set.class))).thenReturn(customerAccounts);
            Mockito.when(transactionService.getTransactionsForAccounts(any(List.class))).thenReturn(emptyList());

            final List<AccountDetails> accountsDetails = customerAccountService.getCustomerAccountsDetails(null);

            Assertions.assertNotNull(accountsDetails);

            Mockito.verify(customerRepository, times(1)).findAll();
            Mockito.verify(customerAccountRepository, times(1)).retrieveByCustomers(any(Set.class));
            Mockito.verify(transactionService, times(1)).getTransactionsForAccounts(any(List.class));
        }

        @Test
        void getCustomerAccountsDetails_skipTransaction_success() {
            Mockito.when(customerRepository.retrieve(customer.getCustomerId())).thenReturn(customer);
            Mockito.when(customerAccountRepository.retrieveByCustomers(any(Set.class))).thenReturn(emptyList());

            final List<AccountDetails> accountsDetails = customerAccountService.getCustomerAccountsDetails(customer.getCustomerId());

            Assertions.assertNotNull(accountsDetails);

            Mockito.verify(customerRepository, times(1)).retrieve(customer.getCustomerId());
            Mockito.verify(customerAccountRepository, times(1)).retrieveByCustomers(any(Set.class));
            Mockito.verify(transactionService, times(0)).getTransactionsForAccounts(any(List.class));
        }
    }

    @TestConfiguration
    @ComponentScan(basePackages = "com.av.finance.account.app.service")
    static class ApplicationServiceTestConfiguration {
    }
}
