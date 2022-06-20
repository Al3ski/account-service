package com.av.finance.account.infrastructure.persistence.account;

import com.av.finance.account.domain.account.CustomerAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@Import(CustomerAccountPersistenceTestConfiguration.class)
class CustomerAccountMapperTest {

    @Autowired
    private CustomerAccountMapper customerAccountMapper;

    @Autowired
    private CustomerAccountFactory customerAccountFactory;

    @Test
    void mapEntityToCustomerAccount_success() {
        final CustomerAccountEntity customerAccountEntity = customerAccountFactory.createCustomerAccountEntity();

        final CustomerAccount customerAccount = customerAccountMapper.toCustomerAccount(customerAccountEntity);

        assertEquals(customerAccountEntity.getCustomerId(), customerAccount.getCustomerId());
        assertEquals(customerAccountEntity.getAccountId(), customerAccount.getAccountId());
        assertEquals(customerAccountEntity.getAccountType(), customerAccount.getAccountType().name());
        assertEquals(customerAccountEntity.getBalance(), customerAccount.getBalance());
    }

    @Test
    void mapEntityToCustomerAccount_ifNullEntity_success() {
        final CustomerAccount customerAccount = customerAccountMapper.toCustomerAccount(null);

        Assertions.assertNull(customerAccount);
    }

    @Test
    void mapCustomerAccountToEntity_success() {
        final CustomerAccount customerAccount = customerAccountFactory.createCustomerAccount();

        final CustomerAccountEntity customerAccountEntity = customerAccountMapper.toEntity(customerAccount);

        assertEquals(customerAccount.getCustomerId(), customerAccountEntity.getCustomerId());
        assertEquals(customerAccount.getAccountId(), customerAccountEntity.getAccountId());
        assertEquals(customerAccount.getAccountType().name(), customerAccountEntity.getAccountType());
        assertEquals(customerAccount.getBalance(), customerAccountEntity.getBalance());
    }

    @Test
    void mapCustomerAccountToEntity_ifNullAccount_success() {
        final CustomerAccountEntity customerAccountEntity = customerAccountMapper.toEntity(null);

        Assertions.assertNull(customerAccountEntity);
    }

    @Test
    void mapEntitiesToCustomerAccounts_success() {
        final List<CustomerAccountEntity> customerAccountEntities = customerAccountFactory.createCustomerAccountEntities();

        final List<CustomerAccount> customerAccounts = customerAccountMapper.toCustomerAccounts(customerAccountEntities);

        IntStream.range(0, customerAccountEntities.size())
                .forEach(index -> {
                    assertEquals(customerAccountEntities.get(index).getCustomerId(), customerAccounts.get(index).getCustomerId());
                    assertEquals(customerAccountEntities.get(index).getAccountId(), customerAccounts.get(index).getAccountId());
                    assertEquals(customerAccountEntities.get(index).getAccountType(), customerAccounts.get(index).getAccountType().name());
                    assertEquals(customerAccountEntities.get(index).getBalance(), customerAccounts.get(index).getBalance());
                });
    }

    @Test
    void mapEntitiesToCustomerAccounts_ifEmptyEntities_success() {
        final List<CustomerAccountEntity> customerAccountEntities = Collections.emptyList();

        final List<CustomerAccount> customerAccounts = customerAccountMapper.toCustomerAccounts(customerAccountEntities);

        assertNotNull(customerAccounts);
        assertEquals(0, customerAccounts.size());
    }

    @Test
    void mapEntitiesToCustomerAccounts_ifNullEntities_success() {
        final List<CustomerAccount> customerAccounts = customerAccountMapper.toCustomerAccounts(null);

        assertNull(customerAccounts);
    }

    @Test
    void mapCustomerAccountsToEntities_success() {
        final List<CustomerAccount> customerAccounts = customerAccountFactory.createCustomerAccounts();

        final List<CustomerAccountEntity> customerAccountEntities = customerAccountMapper.toEntities(customerAccounts);

        IntStream.range(0, customerAccounts.size())
                .forEach(index -> {
                    assertEquals(customerAccounts.get(index).getCustomerId(), customerAccountEntities.get(index).getCustomerId());
                    assertEquals(customerAccounts.get(index).getAccountId(), customerAccountEntities.get(index).getAccountId());
                    assertEquals(customerAccounts.get(index).getAccountType().name(), customerAccountEntities.get(index).getAccountType());
                    assertEquals(customerAccounts.get(index).getBalance(), customerAccountEntities.get(index).getBalance());
                });
    }

    @Test
    void mapCustomerAccountsToEntities_ifEmptyAccounts_success() {
        final List<CustomerAccount> customerAccounts = Collections.emptyList();

        final List<CustomerAccountEntity> customerAccountEntities = customerAccountMapper.toEntities(customerAccounts);

        assertNotNull(customerAccountEntities);
        assertEquals(0, customerAccountEntities.size());
    }

    @Test
    void mapCustomerAccountsToEntities_ifNullAccounts_success() {
        final List<CustomerAccountEntity> customerAccountEntities = customerAccountMapper.toEntities(null);

        assertNull(customerAccountEntities);
    }
}