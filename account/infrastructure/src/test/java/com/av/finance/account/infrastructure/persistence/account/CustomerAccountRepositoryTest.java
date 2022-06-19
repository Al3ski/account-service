package com.av.finance.account.infrastructure.persistence.account;

import com.av.finance.account.domain.account.CustomerAccount;
import com.av.finance.account.domain.account.repository.CustomerAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.groupingBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@Import(CustomerAccountPersistenceTestConfiguration.class)
class CustomerAccountRepositoryTest {

    @Autowired
    private CustomerAccountFactory customerAccountFactory;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @SpyBean
    private CustomerAccountMapper customerAccountMapper;

    @MockBean
    private CustomerAccountDao customerAccountDao;

    @Test
    void retrieveCustomerAccountById_success() {
        final CustomerAccountEntity accountEntity = customerAccountFactory.createCustomerAccountEntity();

        Mockito.when(customerAccountDao.findById(accountEntity.getAccountId())).thenReturn(Optional.of(accountEntity));

        final CustomerAccount customerAccount = customerAccountRepository.retrieve(accountEntity.getAccountId());

        assertEquals(accountEntity.getAccountId(), customerAccount.getAccountId());

        Mockito.verify(customerAccountDao, times(1))
                .findById(accountEntity.getAccountId());
        Mockito.verify(customerAccountMapper, times(1))
                .toCustomerAccount(any(CustomerAccountEntity.class));
    }

    @Test
    void retrieveAccountsByCustomer_success() {
        final List<CustomerAccountEntity> customerAccountEntities = customerAccountFactory.createCustomerAccountEntities();
        final UUID customerId = customerAccountEntities.get(0).getCustomerId();

        Mockito.when(customerAccountDao.findAllByCustomerId(customerId)).thenReturn(customerAccountEntities);

        final List<CustomerAccount> customerAccounts = customerAccountRepository.retrieveByCustomer(customerId);

        customerAccounts.forEach(customerAccount -> assertEquals(customerId, customerAccount.getCustomerId()));

        Mockito.verify(customerAccountDao, times(1))
                .findAllByCustomerId(customerId);
        Mockito.verify(customerAccountMapper, times(1))
                .toCustomerAccounts(any(List.class));
    }

    @Test
    void retrieveAccountsByMultipleCustomers_success() {
        final List<CustomerAccountEntity> customerAccountEntities = customerAccountFactory.createCustomerAccountEntities(true);
        final Map<UUID, List<CustomerAccountEntity>> groupedEntities = customerAccountEntities.stream()
                .collect(groupingBy(CustomerAccountEntity::getCustomerId));

        Mockito.when(customerAccountDao.findAllByCustomerIdIn(groupedEntities.keySet())).thenReturn(customerAccountEntities);

        final List<CustomerAccount> customerAccounts = customerAccountRepository.retrieveByCustomers(groupedEntities.keySet());

        customerAccounts.forEach(customerAccount ->
                Assertions.assertNotNull(groupedEntities.get(customerAccount.getCustomerId()))
        );

        Mockito.verify(customerAccountDao, times(1))
                .findAllByCustomerIdIn(groupedEntities.keySet());
        Mockito.verify(customerAccountMapper, times(1))
                .toCustomerAccounts(any(List.class));
    }

    @Test
    void saveCustomerAccount_success() {
        final CustomerAccount customerAccount = customerAccountFactory.createCustomerAccount();

        customerAccountRepository.save(customerAccount);

        Mockito.verify(customerAccountDao, times(1))
                .save(any(CustomerAccountEntity.class));
        Mockito.verify(customerAccountMapper, times(1))
                .toEntity(customerAccount);
    }
}