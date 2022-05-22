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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final TransactionService transactionService;

    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository customerAccountRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public UUID openAccount(UUID customerId, CustomerAccountType accountType, BigDecimal initialCredit) {
        final Customer customer = customerRepository.retrieve(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with id: " + customerId + " was not found in the system");
        }

        final CustomerAccount customerAccount = CustomerAccount.open(customerId, accountType, initialCredit);
        customerAccountRepository.save(customerAccount);

        if (BigDecimal.ZERO.compareTo(initialCredit) < 0) {
            transactionService.createTransaction(
                    customerAccount.getAccountId(), TransactionType.INITIAL, initialCredit, "");
        }

        return customerAccount.getAccountId();
    }
}
