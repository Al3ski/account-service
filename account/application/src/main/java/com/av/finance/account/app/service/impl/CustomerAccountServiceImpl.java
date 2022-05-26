package com.av.finance.account.app.service.impl;

import com.av.finance.account.app.dto.AccountDetails;
import com.av.finance.account.app.dto.TxDetails;
import com.av.finance.account.app.external.TransactionExternalService;
import com.av.finance.account.app.service.CustomerAccountService;
import com.av.finance.account.app.service.CustomerNotFoundException;
import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.CustomerAccountType;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import com.av.finance.account.domain.customer.Customer;
import com.av.finance.account.domain.customer.repository.CustomerRepository;
import com.av.finance.account.domain.transaction.TransactionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final TransactionExternalService transactionExternalService;

    private final CustomerRepository customerRepository;
    private final CustomerAccountRepository customerAccountRepository;

    @Override
    public List<AccountDetails> getCustomerAccountsDetails(UUID customerId) {
        final List<Customer> customers = customerId == null ?
                customerRepository.findAll() : Collections.singletonList(customerRepository.retrieve(customerId));

        final Map<UUID, Customer> idToCustomer = customers.stream()
                .filter(Objects::nonNull)
                .collect(toMap(Customer::getCustomerId, identity()));
        final List<CustomerAccount> accounts = customerAccountRepository.retrieveByCustomers(idToCustomer.keySet());

        final List<UUID> accountIds = toIds(accounts, CustomerAccount::getAccountId);
        final Map<UUID, List<TxDetails>> accountTxs = accountIds.isEmpty() ? Collections.emptyMap()
                : transactionExternalService.getTransactionsForAccounts(accountIds)
                .stream()
                .collect(groupingBy(TxDetails::getAccountId));

        return accounts.stream()
                .map(account -> AccountDetails.builder()
                        .accountId(account.getAccountId())
                        .name(idToCustomer.get(account.getCustomerId()).getName())
                        .surname(idToCustomer.get(account.getCustomerId()).getSurname())
                        .balance(account.getBalance())
                        .accountTransactions(accountTxs.get(account.getAccountId()))
                        .build())
                .collect(toList());
    }

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
            transactionExternalService.createTransaction(TxDetails.builder()
                    .accountId(customerAccount.getAccountId())
                    .txType(TransactionType.INITIAL)
                    .amount(initialCredit)
                    .details("Initial credit request")
                    .build());
        }

        return customerAccount.getAccountId();
    }

    private <T> List<UUID> toIds(Iterable<T> source, Function<T, UUID> converter) {
        return StreamSupport.stream(source.spliterator(), false)
                .filter(Objects::nonNull)
                .map(converter)
                .collect(toList());
    }
}
