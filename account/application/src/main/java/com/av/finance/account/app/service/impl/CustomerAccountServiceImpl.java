package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.app.service.CustomerNotFoundException;
import com.av.finance.account.app.service.TransactionService;
import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.CustomerAccountType;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import com.av.finance.account.domain.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final TransactionService transactionService;

    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public UUID openAccount(UUID customerId, CustomerAccountType accountType, BigDecimal initialCredit) {
        final Customer customer = customerRepository.retrieve(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id: " + customerId + " was not found in the system");
        }

        final CustomerAccount customerAccount = CustomerAccount.open(customerId, accountType, initialCredit);
        customerAccountRepository.save(customerAccount);
        log.info("{} account successfully created for customer: {}", accountType.toUpperCamelCase(), customerId);

        if (BigDecimal.ZERO.compareTo(initialCredit) < 0) {
            transactionService.createTransaction(customerId, TransactionType.INITIAL, initialCredit, "");
        }

        return customerAccount.getAccountId();
    }
}
