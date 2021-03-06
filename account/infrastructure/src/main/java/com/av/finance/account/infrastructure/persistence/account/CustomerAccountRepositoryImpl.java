package com.av.finance.account.infrastructure.persistence.account;

import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomerAccountRepositoryImpl implements CustomerAccountRepository {

    private final CustomerAccountDao customerAccountDao;
    private final CustomerAccountMapper mapper;

    @Override
    public CustomerAccount retrieve(UUID accountId) {
        final CustomerAccountEntity entity = customerAccountDao.findById(accountId).orElse(null);
        final CustomerAccount customerAccount = mapper.toCustomerAccount(entity);
        log.debug("Found customer account: {} for id: {}", customerAccount, accountId);
        return customerAccount;
    }

    @Override
    public List<CustomerAccount> retrieveByCustomer(UUID customerId) {
        final List<CustomerAccountEntity> entities = customerAccountDao.findAllByCustomerId(customerId);
        final List<CustomerAccount> customerAccounts = mapper.toCustomerAccounts(entities);
        log.debug("Found customer accounts: {} for customer: {}", customerAccounts, customerId);
        return customerAccounts;
    }

    @Override
    public List<CustomerAccount> retrieveByCustomers(Iterable<UUID> customerIds) {
        final List<CustomerAccountEntity> entities = customerAccountDao.findAllByCustomerIdIn(customerIds);
        final List<CustomerAccount> customerAccounts = mapper.toCustomerAccounts(entities);
        log.debug("Found customer accounts: {} for customers: {}", customerAccounts, customerIds);
        return customerAccounts;
    }

    @Override
    public void save(CustomerAccount customerAccount) {
        customerAccountDao.save(mapper.toEntity(customerAccount));
        log.debug("Customer account: {} successfully created", customerAccount.getAccountId());
    }
}
